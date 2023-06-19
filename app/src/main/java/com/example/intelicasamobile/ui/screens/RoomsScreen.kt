package com.example.intelicasamobile.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.ShapeDropdownSelector
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.devices.DeviceCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RoomsScreen(
    devicesModel: DevicesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(),
    minWidth: Int = R.dimen.card_small,
) {
    val roomsState by roomsModel.roomsUiState.collectAsState()
    val devicesState by devicesModel.devicesUiState.collectAsState()
    var currentIndex by remember { mutableStateOf(roomsState.rooms.indexOf(roomsState.currentRoom)) }
    var offsetX by remember { mutableStateOf(0f) }
    var pixelWidth by remember { mutableStateOf(0) }
    val snackbarHostState = rememberScaffoldState().snackbarHostState
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        devicesModel.fetchDevices()
        roomsModel.fetchRooms()
    }

    LaunchedEffect(devicesState.showSnackBar) {
        if (devicesState.showSnackBar) {
            val result = snackbarHostState.showSnackbar("", duration = SnackbarDuration.Short)
            if (result == SnackbarResult.Dismissed) {
                devicesModel.dismissSnackBar()
            }
        }
    }


    SwipeRefresh(state = rememberSwipeRefreshState(roomsState.isLoading || devicesState.isLoading),
        onRefresh = {
            roomsModel.fetchRooms()
            devicesModel.fetchDevices()
        }) {
        Surface(color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { pixelWidth = it.size.width }
                .draggable(orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    },
                    onDragStopped = {
                        if (offsetX > pixelWidth / 3) {
                            currentIndex =
                                (currentIndex - 1 + roomsState.rooms.size) % roomsState.rooms.size
                            roomsModel.setCurrentRoom(roomsState.rooms[currentIndex])

                        } else if (offsetX < -pixelWidth / 3) {
                            currentIndex = (currentIndex + 1) % roomsState.rooms.size
                            roomsModel.setCurrentRoom(roomsState.rooms[currentIndex])

                        }
                        offsetX = 0f
                    })
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = {
                            Snackbar(
                                content = { Text(stringResource(R.string.devices_snackbar_message), color= Color.White) },
                                action = {
                                    TextButton(
                                        onClick = { devicesModel.dismissSnackBar() },
                                        content = { Text(stringResource(id = R.string.dismiss)) }
                                    )
                                },
                                modifier = Modifier.zIndex(10f)
                            )
                        }
                    )
                },
            ) { p ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(p)) {
                    if (roomsState.rooms.isNotEmpty()) {
                        currentIndex = roomsState.rooms.indexOf(roomsState.currentRoom)

                        DevicesList(
                            offset = DpOffset(
                                with(LocalDensity.current) { (-pixelWidth + offsetX * 1.5f).toDp() },
                                0.dp
                            ),
                            roomsModel = roomsModel,
                            devicesModel = devicesModel,
                            index = (currentIndex - 1 + roomsState.rooms.size) % roomsState.rooms.size,
                            minWidth = minWidth
                        )
                        DevicesList(
                            offset = DpOffset(
                                with(LocalDensity.current) { (offsetX * 1.5f).toDp() }, 0.dp
                            ),
                            setIndex = { index ->
                                currentIndex = index
                                roomsModel.setCurrentRoom(roomsState.rooms[currentIndex])
                            },
                            roomsModel = roomsModel,
                            devicesModel = devicesModel,
                            index = currentIndex,
                            minWidth = minWidth
                        )
                        DevicesList(
                            offset = DpOffset(
                                with(LocalDensity.current) { (pixelWidth + offsetX * 1.5f).toDp() },
                                0.dp
                            ),
                            roomsModel = roomsModel,
                            devicesModel = devicesModel,
                            index = (currentIndex + 1) % roomsState.rooms.size,
                            minWidth = minWidth
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DevicesList(
    devicesModel: DevicesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(),
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    index: Int,
    setIndex: (Int) -> Unit = {},
    minWidth: Int = R.dimen.card_small,
) {
    val roomsState by roomsModel.roomsUiState.collectAsState()
    val devicesState by devicesModel.devicesUiState.collectAsState()

    val dropdownRoomStateHolder = rememberDropdownSelectorState(items = roomsState.rooms.map {
        DropdownSelectorItem(
            label = if (it.nameId != null) stringResource(id = it.nameId) else it.name,
            value = it,
            icon = it.roomType.imageResourceId
        )
    }, onItemSelected = {
        roomsModel.setCurrentRoom(it.value as Room)
        setIndex(roomsState.rooms.indexOf(it.value))
    })

    dropdownRoomStateHolder.onSelected(
        DropdownSelectorItem(
            label = if (roomsState.rooms[index].nameId != null) stringResource(id = roomsState.rooms[index].nameId!!) else roomsState.rooms[index].name,
            value = roomsState.rooms[index],
            icon = roomsState.rooms[index].roomType.imageResourceId
        )
    )

    val stateGrid = rememberLazyGridState()

    Column(
        modifier = Modifier
            .offset(x = offset.x, y = offset.y)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ShapeDropdownSelector(
            stateHolder = dropdownRoomStateHolder,
            shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 0.dp),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 24
        )

        Box(modifier = Modifier.weight(1f)) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(dimensionResource(id = minWidth)),
                state = stateGrid,
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            ) {
                items(devicesState.devices.filter {
                    roomsState.rooms[index].id == "all" || roomsState.rooms[index].containsDevice(
                        it.id
                    )
                }) {
                    DeviceCard(device = it, roomsViewModel = roomsModel)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center,
        ) {
            List(roomsState.rooms.size) { i ->
                Button(
                    onClick = {
                        roomsModel.setCurrentRoom(roomsState.rooms[i])
                        setIndex(i)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp)
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (i == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {}
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TabletRoomsScreen(
    devicesModel: DevicesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(),
) {
    RoomsScreen(devicesModel = devicesModel, roomsModel = roomsModel, minWidth = R.dimen.card_large)
}