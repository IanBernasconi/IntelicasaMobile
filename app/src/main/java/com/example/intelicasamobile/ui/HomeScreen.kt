package com.example.intelicasamobile.ui


import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource.devices
import com.example.intelicasamobile.data.Datasource.routines
import com.example.intelicasamobile.ui.devices.DeviceCard
import com.example.intelicasamobile.ui.routines.RoutineHomeCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val configuration = LocalConfiguration.current
        val orientation = configuration.orientation
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            HomeScreenPortrait()
        }else{
            HomeScreenLandscape()
        }

    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun HomeScreenLandscape() {
    val state1 = rememberLazyGridState()
    val state2 = rememberLazyGridState()
    Row {
        Box(Modifier.weight(1f)) {
            Column {
                CategoryCard(
                    title = R.string.routines
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_small)),
                    state = state1,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
                ) {
                    items(routines) { routine ->
                        RoutineHomeCard(
                            routine = routine,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                        )
                    }
                }
            }
        }
        Box(Modifier.weight(1f)) {
            Column {
                CategoryCard(
                    title = R.string.devices
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_medium)),
                    state = state2,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
                ) {
                    items(devices) { device ->
                        DeviceCard(
                            device = device,
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun HomeScreenPortrait() {
    val state1 = rememberLazyGridState()
    val state2 = rememberLazyGridState()
    Column {
        CategoryCard(title = R.string.routines)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_small)),
            state = state1,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
        ) {
            items(routines) { routine ->
                RoutineHomeCard(
                    routine = routine,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
        CategoryCard(title = R.string.devices)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_small)),
            state = state2,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
        ) {
            items(devices) { device ->
                DeviceCard(
                    device = device,
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 1000, heightDp = 600)
@Composable
fun TabletHomeScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        val state1 = rememberLazyGridState()
        val state2 = rememberLazyGridState()
        Column {
            CategoryCard(title = R.string.routines)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_large)),
                state = state1,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
            ) {
                items(routines) { routine ->
                    RoutineHomeCard(
                        routine = routine,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
            CategoryCard(title = R.string.devices)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(dimensionResource(id = R.dimen.card_large)),
                state = state2,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small))
            ) {
                items(devices) { device ->
                    DeviceCard(
                        device = device
                    )
                }
            }

        }
    }
}
