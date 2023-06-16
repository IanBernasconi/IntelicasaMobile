package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACMode
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.DropdownSelectorStateHolder
import com.example.intelicasamobile.ui.components.TextFieldDropdownSelector
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import kotlin.math.floor
import kotlin.math.roundToInt

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun ACDeviceInfoPreview() {
    val device = ACDevice(deviceId = "1", deviceName = "AC Device")
    IntelicasaMobileTheme {
        ACDeviceInfo(
            device = device,
            modifier = Modifier,
        )
    }
}

@Composable
fun ACDeviceInfo(
    modifier: Modifier = Modifier,
    device: ACDevice = viewModel(),
) {
    val scrollState = rememberScrollState()

    val uiState by device.state.collectAsState()

    val dropdownModeStateHolder = rememberDropdownSelectorState(items = ACMode.values().map {
        DropdownSelectorItem(
            label = stringResource(id = it.nameResId), value = it, icon = it.imageResourceId
        )
    },
        label = stringResource(id = R.string.mode),
        loading = device.isLoading(),
        initialItem = DropdownSelectorItem(
            label = stringResource(id = uiState.mode.nameResId),
            value = uiState.mode,
            icon = uiState.mode.imageResourceId
        ),
        onItemSelected = { device.setMode(it.value as ACMode) }
    )
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = modifier.verticalScroll(
                state = scrollState
            )
        ) {
            ACState(device = device, isOn = uiState.isOn, modifier = modifier)
            ACTemperature(device = device, temperature = uiState.temperature, modifier = modifier)
            ACMode(dropdownModeStateHolder, modifier)
            ACVerticalSwing(
                device = device,
                verticalSwing = uiState.verticalSwing,
                modifier = modifier
            )
            ACHorizontalSwing(
                device = device,
                horizontalSwing = uiState.horizontalSwing,
                modifier = modifier
            )
            ACFanSpeed(device = device, fanSpeed = uiState.fanSpeed, modifier = modifier)
        }
    }else {
        Row(
            modifier = modifier.verticalScroll(state = scrollState)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier.fillMaxWidth(0.5f)
            ) {
                ACState(device = device, isOn = uiState.isOn, modifier = modifier)
                ACTemperature(device = device, temperature = uiState.temperature, modifier = modifier)
                ACMode(dropdownModeStateHolder, modifier)
            }
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                ACVerticalSwing(
                    device = device,
                    verticalSwing = uiState.verticalSwing,
                    modifier = modifier
                )
                ACHorizontalSwing(
                    device = device,
                    horizontalSwing = uiState.horizontalSwing,
                    modifier = modifier
                )
                ACFanSpeed(device = device, fanSpeed = uiState.fanSpeed, modifier = modifier)
            }
        }
    }
}

@Composable
fun ACState(
    device: ACDevice = viewModel(),
    isOn: Boolean,
    modifier: Modifier,
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        StateInfo(
            isOn = isOn,
            setIsOn = { device.setIsOn(it) },
            modifier = modifier,
            loading = device.isLoading()
        )
    }
}

@Composable
fun ACTemperature(
    device: ACDevice = viewModel(),
    temperature: Int,
    modifier: Modifier
) {
    var localTemperature by remember { mutableStateOf(temperature) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.5f)
                .padding(start = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.AC_temperature),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = modifier.padding(end = dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${localTemperature}°C", modifier = modifier.padding(end = 4.dp)
                )
                Slider(value = localTemperature.toFloat(),
                    onValueChange = { localTemperature = it.roundToInt() },
                    valueRange = 18f..38f,
                    steps = 0,
                    enabled = !device.isLoading(),
                    modifier = modifier.width(125.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondary
                    ),
                    onValueChangeFinished = { device.setTemperature(localTemperature) })
            }
        }
    }
}

@Composable
fun ACMode(
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
fun ACVerticalSwing(
    device: ACDevice = viewModel(),
    verticalSwing: Int,
    modifier: Modifier
) {
    var localVerticalSwing by remember { mutableStateOf(verticalSwing) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.5f)
                .padding(start = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.AC_vertical_swing),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = modifier.padding(end = dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (localVerticalSwing != 0) "${localVerticalSwing}°" else stringResource(
                        id = R.string.auto_mode
                    ), modifier = modifier.padding(end = 4.dp)
                )
                Slider(
                    value = localVerticalSwing.toFloat(),
                    onValueChange = { localVerticalSwing = floor(it).toInt() },
                    enabled = !device.isLoading(),
                    onValueChangeFinished = { device.setVerticalSwing(localVerticalSwing) },
                    modifier = modifier.width(125.dp),
                    valueRange = 0f..90f,
                    steps = 3,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondary
                    ),
                )
            }
        }
    }
}

@Composable
fun ACHorizontalSwing(
    device: ACDevice = viewModel(),
    horizontalSwing: Int,
    modifier: Modifier
) {
    var localHorizontalSwing by remember { mutableStateOf(horizontalSwing) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(0.5f)
                .padding(start = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.AC_horizontal_swing),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (localHorizontalSwing != -135) "${localHorizontalSwing}°" else stringResource(
                        id = R.string.auto_mode
                    ), modifier = modifier.padding(end = 4.dp)
                )
                Slider(
                    value = localHorizontalSwing.toFloat(),
                    onValueChange = { localHorizontalSwing = floor(it).toInt() },
                    enabled = !device.isLoading(),
                    onValueChangeFinished = { device.setHorizontalSwing(localHorizontalSwing) },
                    modifier = modifier.width(125.dp),
                    valueRange = -135f..90f,
                    steps = 4,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

@Composable
fun ACFanSpeed(
    device: ACDevice = viewModel(),
    fanSpeed: Int,
    modifier: Modifier
) {
    var localFanSpeed by remember { mutableStateOf(fanSpeed) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.AC_fan_speed),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = if (localFanSpeed != 0) "${localFanSpeed}%" else stringResource(
                        id = R.string.auto_mode
                    ), modifier = modifier.padding(end = 4.dp)
                )
                Slider(
                    value = localFanSpeed.toFloat(),
                    onValueChange = { localFanSpeed = floor(it).toInt() },
                    enabled = !device.isLoading(),
                    onValueChangeFinished = { device.setFanSpeed(localFanSpeed) },
                    modifier = modifier.width(125.dp),
                    valueRange = 0f..100f,
                    steps = 3,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

