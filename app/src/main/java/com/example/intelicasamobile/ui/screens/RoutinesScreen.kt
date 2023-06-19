package com.example.intelicasamobile.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.ui.routines.RoutineCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun RoutinesScreen(
    routinesModel: RoutinesViewModel = viewModel(),
    devicesModel: DevicesViewModel = viewModel()
) {
    val routinesState by routinesModel.routinesUiState.collectAsState()

    val snackbarHostState = rememberScaffoldState().snackbarHostState

    LaunchedEffect(Unit) {
        devicesModel.fetchDevices()
        routinesModel.fetchRoutines()
    }

    LaunchedEffect(routinesState.showSnackBar) {
        if (routinesState.showSnackBar) {
            val result = snackbarHostState.showSnackbar("", duration = SnackbarDuration.Short)
            if (result == SnackbarResult.Dismissed) {
                routinesModel.dismissSnackBar()
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(routinesState.isLoading),
        onRefresh = {
            routinesModel.fetchRoutines()
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        snackbar = {
                            Snackbar(
                                content = {  Text(stringResource(R.string.routines_snackbar_message)) },
                                action = {
                                    TextButton(
                                        onClick = { routinesModel.dismissSnackBar() },
                                        content = { Text(stringResource(id = R.string.dismiss)) }
                                    )
                                },
                                modifier = Modifier.zIndex(10f)
                            )
                        }
                    )
                },
            ) { p ->

                val state = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_large)),
                    state = state,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                    modifier = Modifier.padding(p)
                ) {
                    items(routinesState.routines) { routine ->
                        RoutineCard(
                            routine = routine,
                            devicesModel = devicesModel,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun TabletRoutinesScreen(
    routinesModel: RoutinesViewModel = viewModel(),
    devicesModel: DevicesViewModel = viewModel()
) {
    val routinesState by routinesModel.routinesUiState.collectAsState()
    val snackbarHostState = rememberScaffoldState().snackbarHostState


    LaunchedEffect(Unit) {
        devicesModel.fetchDevices()
        routinesModel.fetchRoutines()
    }

    LaunchedEffect(routinesState.showSnackBar) {
        if (routinesState.showSnackBar) {
            val result = snackbarHostState.showSnackbar("", duration = SnackbarDuration.Short)
            if (result == SnackbarResult.Dismissed) {
                routinesModel.dismissSnackBar()
            }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        Snackbar(
                            content = { Text(stringResource(R.string.routines_snackbar_message), color= Color.White) },
                            action = {
                                TextButton(
                                    onClick = { routinesModel.dismissSnackBar() },
                                    content = { Text(stringResource(id = R.string.dismiss)) }
                                )
                            },
                            modifier = Modifier.zIndex(10f)
                        )
                    }
                )
            },
        ) { p ->
            val state = rememberLazyGridState()
            Column(Modifier.padding(p)) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_large)),
                    state = state,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                ) {
                    items(routinesState.routines) { routine ->
                        RoutineCard(
                            routine = routine,
                            devicesModel = devicesModel,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }
}