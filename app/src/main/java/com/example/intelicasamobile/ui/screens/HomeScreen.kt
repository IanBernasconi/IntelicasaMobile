package com.example.intelicasamobile.ui.screens

import android.content.res.Configuration
import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.ui.CategoryCard
import com.example.intelicasamobile.ui.devices.DeviceCard
import com.example.intelicasamobile.ui.routines.RoutineHomeCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen(
    devicesModel: DevicesViewModel = viewModel(),
    routinesModel: RoutinesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel()
) {
    val devicesState by devicesModel.devicesUiState.collectAsState()
    val routinesState by routinesModel.routinesUiState.collectAsState()
    val snackbarHostState = rememberScaffoldState().snackbarHostState

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        devicesModel.fetchDevices()
        routinesModel.fetchRoutines()
        roomsModel.fetchRooms()
    }
    LaunchedEffect(devicesState.showSnackBar) {
        if (devicesState.showSnackBar) {
            val result = snackbarHostState.showSnackbar("")
            if (result == SnackbarResult.Dismissed) {
                devicesModel.dismissSnackBar()
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(devicesState.isLoading || routinesState.isLoading),
        onRefresh = {
            devicesModel.fetchDevices()
            routinesModel.fetchRoutines()
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = MaterialTheme.colorScheme.background
        ) {
                val configuration = LocalConfiguration.current
                val orientation = configuration.orientation
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    HomeScreenPortrait(
                        devicesModel = devicesModel,
                        routinesModel = routinesModel,
                        modifier = Modifier
                    )
                } else {
                    HomeScreenLandscape(
                        devicesModel = devicesModel,
                        routinesModel = routinesModel,
                        modifier = Modifier
                    )
                }

            }
            if(devicesState.showSnackBar) {
                Snackbar(
                    content = { Text(stringResource(R.string.snackbar_message)) },
                    action = {
                        TextButton(
                            onClick = { devicesModel.dismissSnackBar() },
                            content = { Text("Dismiss") }
                        )
                    },
                    modifier = Modifier.zIndex(10f)
                )
            }
        }

}

@Composable
fun HomeScreenPortrait(
    devicesModel: DevicesViewModel = viewModel(),
    routinesModel: RoutinesViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state1 = rememberLazyGridState()
    val state2 = rememberLazyGridState()
    Column {
        RoutinesHomeList(state1 = state1, R.dimen.card_small, routinesModel)
        DevicesHomeList(state2 = state2, R.dimen.card_small, devicesModel)
    }
}


@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun HomeScreenLandscape(
    devicesModel: DevicesViewModel = viewModel(),
    routinesModel: RoutinesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(), modifier: Modifier = Modifier
) {
    val state1 = rememberLazyGridState()
    val state2 = rememberLazyGridState()
    Row {
        Box(Modifier.weight(1f)) {
            Column {
                RoutinesHomeList(state1, R.dimen.card_medium,routinesModel)
            }

        }
        Box(Modifier.weight(1f)) {
            Column {
                DevicesHomeList(state2, R.dimen.card_medium, devicesModel)
            }
        }
    }

}

@Composable
private fun DevicesHomeList(
    state2: LazyGridState,
    @DimenRes minWidth: Int,
    model: DevicesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(),
) {

    val state by model.devicesUiState.collectAsState()

    CategoryCard(
        title = R.string.devices
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = minWidth)),
        state = state2,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium))
    ) {
        items(state.devices.filter { it.meta.favorite }) { device ->
            DeviceCard(
                device = device,
                roomsViewModel = roomsModel,
            )
        }
    }
}


@Composable
private fun RoutinesHomeList(
    state1: LazyGridState,
    @DimenRes minWidth: Int,
    model: RoutinesViewModel = viewModel()
) {

    val state by model.routinesUiState.collectAsState()

    CategoryCard(
        title = R.string.routines
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = minWidth)),
        state = state1,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
    ) {
        items(state.routines.filter { it.meta.favorite }) { routine ->
            RoutineHomeCard(
                routine = routine,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}


@Preview(showBackground = true, widthDp = 1000, heightDp = 600)
@Composable
fun TabletHomeScreen(
    devicesModel: DevicesViewModel = viewModel(),
    routinesModel: RoutinesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        devicesModel.fetchDevices()
        routinesModel.fetchRoutines()
        roomsModel.fetchRooms()
    }

    val state1 = rememberLazyGridState()
    val state2 = rememberLazyGridState()
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    )
    {
        Row {
            Box(Modifier.weight(1f)) {
                Column {
                    RoutinesHomeList(state1, R.dimen.card_medium, routinesModel)
                }

            }
            Box(Modifier.weight(1f)) {
                Column {
                    DevicesHomeList(state2, R.dimen.card_medium, devicesModel, roomsModel)
                }
            }
        }
    }
}
