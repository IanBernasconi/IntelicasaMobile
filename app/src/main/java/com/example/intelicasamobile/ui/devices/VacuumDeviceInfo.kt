package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.intelicasamobile.ui.components.DropdownSelectorStateHolder
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
        }, label = stringResource(id = R.string.VCI_room),
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

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = modifier.verticalScroll(
                state = scrollState
            )
        ) {
            VacuumCharger(state= uiState.state, batteryLevel = uiState.batteryLevel, modifier= modifier)
            VacuumDockLocation(device= device,state = uiState.state, batteryLevel = uiState.batteryLevel, modifier = modifier)
            VacuumModeDropdown(dropdownModeStateHolder = dropdownModeStateHolder, modifier = modifier)
            VacuumLocationStateDropdown(dropdownLocationStateHolder = dropdownLocationStateHolder, modifier = modifier)
        }
    }else {
        Row(
            modifier = modifier.verticalScroll(state = scrollState)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                VacuumCharger(state= uiState.state, batteryLevel = uiState.batteryLevel, modifier= modifier)
                VacuumDockLocation(device= device,state = uiState.state, batteryLevel = uiState.batteryLevel, modifier = modifier)
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                VacuumModeDropdown(dropdownModeStateHolder = dropdownModeStateHolder, modifier = modifier)
                VacuumLocationStateDropdown(dropdownLocationStateHolder = dropdownLocationStateHolder, modifier = modifier)
            }
        }
    }

}

@Composable
fun VacuumCharger(
    state: VacuumStateEnum,
    batteryLevel: Int,
    modifier: Modifier
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
                text = stringResource(id = state.nameResId),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Row(modifier = Modifier) {
            Icon(
                imageVector =
                if (state != VacuumStateEnum.CHARGING)
                    when (batteryLevel) {
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
                text = "${batteryLevel}%", style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun VacuumDockLocation(
    device: VacuumDevice = viewModel(),
    state: VacuumStateEnum,
    batteryLevel: Int,
    modifier: Modifier
) {
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
                enabled = !device.isLoading() && state != VacuumStateEnum.CLEANING && batteryLevel > 5
            ) {
                if (batteryLevel < 5) {
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
                enabled = !device.isLoading() && state == VacuumStateEnum.CLEANING
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
                enabled = !device.isLoading() && state != VacuumStateEnum.CHARGING
            ) {
                Text(text = stringResource(id = R.string.VCA_charge))
            }
        }
    }

}

@Composable
fun VacuumModeDropdown(
    dropdownModeStateHolder: DropdownSelectorStateHolder,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldDropdownSelector(stateHolder = dropdownModeStateHolder)
    }
}

@Composable
fun VacuumLocationStateDropdown(
    dropdownLocationStateHolder: DropdownSelectorStateHolder,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldDropdownSelector(stateHolder = dropdownLocationStateHolder)
    }
}