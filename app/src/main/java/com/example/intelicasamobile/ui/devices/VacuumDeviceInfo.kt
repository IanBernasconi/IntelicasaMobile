package com.example.intelicasamobile.ui.devices

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Battery0Bar
import androidx.compose.material.icons.outlined.Battery1Bar
import androidx.compose.material.icons.outlined.Battery2Bar
import androidx.compose.material.icons.outlined.Battery3Bar
import androidx.compose.material.icons.outlined.Battery4Bar
import androidx.compose.material.icons.outlined.Battery5Bar
import androidx.compose.material.icons.outlined.Battery6Bar
import androidx.compose.material.icons.outlined.BatteryAlert
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.BatteryFull
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.VacuumCleanMode
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.model.VacuumStateEnum
import com.example.intelicasamobile.ui.components.TextFieldDropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun VacummDeviceInfoPreview() {
    val device = VacuumDevice(deviceId = "1", deviceName = "Vacuum")
    IntelicasaMobileTheme {
        VacuumDeviceInfo(
            device = device,
            modifier = Modifier,
        )
    }
}

@Composable
fun VacuumDeviceInfo(
    modifier: Modifier = Modifier,
    device: VacuumDevice = viewModel(),
    roomsViewModel: RoomsViewModel = viewModel()
) {

    val scrollState = rememberScrollState()
    val uiState by device.state.collectAsState()
    val roomsState by roomsViewModel.roomsUiState.collectAsState()

    val dropdownModeStateHolder = rememberDropdownSelectorState(
        items = VacuumCleanMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.mode),
        loading = device.isLoading(),
        onItemSelected = { device.setMode(it.value as VacuumCleanMode) },
        initialItem = DropdownSelectorItem(
            label = stringResource(id = uiState.mode.nameResId),
            value = uiState.mode,
            icon = uiState.mode.imageResourceId
        )
    )

    val dropdownLocationStateHolder = rememberDropdownSelectorState(
        items = roomsState.rooms.filter { it.id != "all" }.map {
            DropdownSelectorItem(
                label = it.name,
                value = it,
                icon = it.roomType.imageResourceId
            )
        }, label = stringResource(id = R.string.room),
        loading = device.isLoading(),
        onItemSelected = { device.setLocation((it.value as Room).id) },
        initialItem = roomsViewModel.getRoomById(uiState.location)?.let {
            DropdownSelectorItem(
                label = it.name,
                value = it,
                icon = it.roomType.imageResourceId
            )
        }
    )

    Column(
        modifier = modifier.verticalScroll(
            state = scrollState
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = uiState.state.nameResId),
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Row(modifier = Modifier) {
                Icon(
                    imageVector =
                    if (uiState.state != VacuumStateEnum.CHARGING)
                        when (uiState.batteryLevel) {
                            in 0..5 -> Icons.Outlined.Battery0Bar
                            in 6..15 -> Icons.Outlined.Battery1Bar
                            in 15..30 -> Icons.Outlined.Battery2Bar
                            in 31..45 -> Icons.Outlined.Battery3Bar
                            in 45..60 -> Icons.Outlined.Battery4Bar
                            in 61..75 -> Icons.Outlined.Battery5Bar
                            in 75..90 -> Icons.Outlined.Battery6Bar
                            else -> Icons.Outlined.BatteryFull
                        }
                    else Icons.Outlined.BatteryChargingFull,
                    contentDescription = null,
                    modifier = Modifier.width(24.dp)
                )
                Text(
                    text = "${uiState.batteryLevel}%", style = TextStyle(fontSize = 16.sp)
                )
            }
        }

        if (device.dockLocationId == null) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.padding_medium),
                        dimensionResource(id = R.dimen.padding_small)
                    ),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = stringResource(id = R.string.VCI_no_room_message))
            }
        } else {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        device.setState(VacuumStateEnum.CLEANING)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                    ),
                    shape = RoundedCornerShape(5.dp),
                    enabled = !device.isLoading() && uiState.state != VacuumStateEnum.CLEANING && uiState.batteryLevel > 5
                ) {
                    if (uiState.batteryLevel < 5) {
                        Icon(
                            imageVector = Icons.Outlined.BatteryAlert,
                            contentDescription = null,
                            modifier = Modifier.width(24.dp)
                        )
                    } else {
                        Text(text = stringResource(id = R.string.VCA_clean))
                    }
                }
                Button(
                    onClick = {
                        device.setState(VacuumStateEnum.PAUSED)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                    ),
                    shape = RoundedCornerShape(5.dp),
                    enabled = !device.isLoading() && uiState.state == VacuumStateEnum.CLEANING
                ) {
                    Text(text = stringResource(id = R.string.VCA_pause))
                }
                Button(
                    onClick = {
                        device.setState(VacuumStateEnum.CHARGING)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                    ),
                    shape = RoundedCornerShape(5.dp),
                    enabled = !device.isLoading() && uiState.state != VacuumStateEnum.CHARGING
                ) {
                    Text(text = stringResource(id = R.string.VCA_charge))
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            TextFieldDropdownSelector(stateHolder = dropdownModeStateHolder)
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            TextFieldDropdownSelector(stateHolder = dropdownLocationStateHolder)
        }
    }
}