package com.example.intelicasamobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomScreenState
import com.example.intelicasamobile.ui.components.TextFieldDropdownSelector
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
    IntelicasaMobileTheme() {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val roomScreenState by state.state.collectAsState()
            val dropdownRoomStateHolder =
                rememberDropdownSelectorState(
                    items = Datasource.rooms.map {
                        DropdownSelectorItem(
                            label = it.name,
                            value = it,
                            icon = it.roomType.imageResourceId
                        )
                    },
                    onItemSelected = { state.setRoom(it.value as Room) },
                    initialItem = DropdownSelectorItem(
                        label = roomScreenState.name,
                        value = roomScreenState,
                        icon = roomScreenState.roomType.imageResourceId
                    )
                )

            val stateGrid = rememberLazyGridState()
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    ShapeDropdownSelector(
                        stateHolder = dropdownRoomStateHolder,
                        shape = RoundedCornerShape( bottomEnd = 20.dp))
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = stateGrid,
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                ) {
                    items(Datasource.rooms) { room ->
                        room.devices.forEach {device ->
                            DeviceCard(device = device)
                        }
                    }
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