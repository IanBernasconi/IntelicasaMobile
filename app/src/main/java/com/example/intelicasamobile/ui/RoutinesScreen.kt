package com.example.intelicasamobile.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.ui.routines.RoutineCard
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun RoutinesScreen(
    routinesModel: RoutinesViewModel = viewModel(),
    devicesModel: DevicesViewModel = viewModel()
) {
    val routinesState by routinesModel.routinesUiState.collectAsState()

    LaunchedEffect(Unit) {
        routinesModel.getRoutines()
    }

    IntelicasaMobileTheme() {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val state = rememberLazyGridState()
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun TabletRoutinesScreen(
    routinesModel: RoutinesViewModel = viewModel(),
    devicesModel: DevicesViewModel = viewModel()
) {
    val routinesState by routinesModel.routinesUiState.collectAsState()
    IntelicasaMobileTheme() {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val state = rememberLazyGridState()
            Column {
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