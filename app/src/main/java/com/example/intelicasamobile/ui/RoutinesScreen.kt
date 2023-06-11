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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource
import com.example.intelicasamobile.ui.routines.RoutineCard
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun RoutinesScreen() {
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
                    items(Datasource.routines) { routine ->
                        RoutineCard(
                            routine = routine,
                            Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun TabletRoutinesScreen() {
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
                    items(Datasource.routines) { routine ->
                        RoutineCard(
                            routine = routine,
                            Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }
}