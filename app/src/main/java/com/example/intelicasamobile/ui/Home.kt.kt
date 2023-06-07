package com.example.intelicasamobile.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource.devices
import com.example.intelicasamobile.data.Datasource.routines
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import com.example.intelicasamobile.ui.devices.DeviceCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Home() {
    IntelicasaMobileTheme {
        Scaffold(
            topBar = {
                IntellicasaTopAppBar()
            }
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                val state1 = rememberLazyGridState()
                val state2 = rememberLazyGridState()
                Column(){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = state1,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        contentPadding = it
                    ) {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            CategoryCard(title = R.string.routines)
                        }

                        items(routines) { routine ->
                            RoutineCard(
                                routine = routine,
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                            )
                        }
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = state2,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                        contentPadding = it
                    ) {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            CategoryCard(title = R.string.devices)
                        }

                        items(devices) { device ->
                            DeviceCard(
                                device = device,
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                            )
                        }
                    }

                }
            }
        }
    }
}
