package com.example.intelicasamobile.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomScreenState
import com.example.intelicasamobile.model.RoomsScreen
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.ShapeDropdownSelector
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.devices.DeviceCard
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun RoomsScreen(
    state: RoomScreenState = viewModel(),
) {
    val currentRoom by state.state.collectAsState()

    var offsetX by remember { mutableStateOf(0f) }
    var pixelWidth by remember { mutableStateOf(0) }

    val allRooms = remember { mutableStateListOf<RoomsScreen>() }
    var currentIndex by remember { mutableStateOf(Datasource.rooms.indexOf(currentRoom)) }

    Datasource.rooms.forEachIndexed { index, room ->
        allRooms.add(RoomsScreen(room, rememberDropdownSelectorState(items = Datasource.rooms.map {
            DropdownSelectorItem(
                label = it.name, value = it, icon = it.roomType.imageResourceId
            )
        }, onItemSelected = {
            state.setRoom(it.value as Room); currentIndex = Datasource.rooms.indexOf(it.value)
        }, changeValueOnSelected = false, initialItem = DropdownSelectorItem(
            label = Datasource.rooms[index].name,
            value = Datasource.rooms[index],
            icon = Datasource.rooms[index].roomType.imageResourceId
        )
        ), index
        )
        )
    }

    IntelicasaMobileTheme() {
        Surface(color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .onGloballyPositioned { pixelWidth = it.size.width }
                .fillMaxHeight()
                .draggable(orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX += delta
                    },
                    onDragStopped = {
                        if (offsetX > pixelWidth / 3) {
                            currentIndex =
                                (currentIndex - 1 + Datasource.rooms.size) % Datasource.rooms.size
                            state.setRoom(Datasource.rooms[currentIndex])
                        } else if (offsetX < -pixelWidth / 3) {
                            currentIndex = (currentIndex + 1) % Datasource.rooms.size
                            state.setRoom(Datasource.rooms[currentIndex])
                        }
                        offsetX = 0f
                    })
        ) {
            Box() {
                DevicesList(
                    allRooms[(currentIndex - 1 + Datasource.rooms.size) % Datasource.rooms.size],
                    state,
                    offset = DpOffset(
                        with(LocalDensity.current) { (-pixelWidth + offsetX * 1.5f).toDp() }, 0.dp
                    ),
                )
                DevicesList(allRooms[currentIndex],
                    state,
                    offset = DpOffset(with(LocalDensity.current) { (offsetX * 1.5f).toDp() }, 0.dp),
                    setIndex = { index ->
                        currentIndex = index
                        state.setRoom(Datasource.rooms[currentIndex])
                    })
                DevicesList(
                    allRooms[(currentIndex + 1) % Datasource.rooms.size],
                    state,
                    offset = DpOffset(
                        with(LocalDensity.current) { (pixelWidth + offsetX * 1.5f).toDp() }, 0.dp
                    ),
                )
            }
        }
    }
}

@Composable
fun DevicesList(
    roomsScreen: RoomsScreen,
    state: RoomScreenState,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    setIndex: (Int) -> Unit = {}
) {
    val stateGrid = rememberLazyGridState()
    Column(modifier = Modifier.offset(x = offset.x, y = offset.y))
    {

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {
            ShapeDropdownSelector(
                stateHolder = roomsScreen.dropdownRoomStateHolder,
                shape = RoundedCornerShape(0.dp, 0.dp, 20.dp, 0.dp),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_small)),
                state = stateGrid,
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            ) {
                items(Datasource.rooms) { room ->
                    room.devices.forEach { device ->
                        DeviceCard(device = device)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.Center,
            ) {
                for (i in 0 until Datasource.rooms.size) {
                    Button(
                        onClick = {
                            state.setRoom(Datasource.rooms[i])
                            setIndex(i)
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(35.dp)
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (i == roomsScreen.index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    ) {}
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TabletRoomsScreen() {
    IntelicasaMobileTheme() {
        RoomsScreen()
    }
}