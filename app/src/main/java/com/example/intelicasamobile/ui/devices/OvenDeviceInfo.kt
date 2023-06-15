package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
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
import com.example.intelicasamobile.model.OvenConvectionMode
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenGrillMode
import com.example.intelicasamobile.model.OvenHeatMode
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.DropdownSelectorStateHolder
import com.example.intelicasamobile.ui.components.TextFieldDropdownSelector
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun OvenDeviceInfoPreview() {
    val device = OvenDevice(deviceId = "1", deviceName = "Horno")
    IntelicasaMobileTheme {
        OvenDeviceInfo(
            device = device,
            modifier = Modifier,
        )
    }
}

@Composable
fun OvenDeviceInfo(
    modifier: Modifier = Modifier,
    device: OvenDevice = viewModel(),
) {
    val scrollState = rememberScrollState()
    val uiState by device.state.collectAsState()


    val dropdownHeatModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenHeatMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it,
                    icon = it.imageResourceId
                )
            },
            label = stringResource(id = R.string.OI_heat_mode),
            loading = device.isLoading(),
            onItemSelected = { device.setHeatMode(it.value as OvenHeatMode) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = uiState.heatMode.nameResId),
                value = uiState.heatMode,
                icon = uiState.heatMode.imageResourceId
            )
        )

    val dropdownConvectionModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenConvectionMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it,
                    icon = it.imageResourceId
                )
            }, label = stringResource(id = R.string.OI_convection_mode),
            loading = device.isLoading(),
            onItemSelected = {
                device.setConvectionMode(it.value as OvenConvectionMode)
            }, initialItem = DropdownSelectorItem(
                label = stringResource(id = uiState.convectionMode.nameResId),
                value = uiState.convectionMode,
                icon = uiState.convectionMode.imageResourceId
            )
        )

    val dropdownGrillModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenGrillMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it,
                    icon = it.imageResourceId
                )
            },
            label = stringResource(id = R.string.OI_grill_mode),
            loading = device.isLoading(),
            onItemSelected = { device.setGrillMode(it.value as OvenGrillMode) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = uiState.grillMode.nameResId),
                value = uiState.grillMode,
                icon = uiState.grillMode.imageResourceId
            )
        )

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = modifier.verticalScroll(
                state = scrollState
            )
        ) {
            OvenState(device = device, isOn = uiState.isOn, modifier = modifier)
            OvenTemperature(device = device, temperature = uiState.temperature, modifier = modifier)
            OvenHeatModeDropdown(
                dropdownHeatModeStateHolder = dropdownHeatModeStateHolder,
                modifier = modifier
            )
            OvenConvectionModeDropdown(
                dropdownConvectionModeStateHolder = dropdownConvectionModeStateHolder,
                modifier = modifier
            )
            OvenGrillModeDropdown(
                dropdownGrillModeStateHolder = dropdownGrillModeStateHolder,
                modifier = modifier
            )
        }
    }else{
        Row(
            modifier = modifier.verticalScroll(state = scrollState)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier.fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.Top
            ) {
                OvenState(device = device, isOn = uiState.isOn, modifier = modifier)
                OvenTemperature(device = device, temperature = uiState.temperature, modifier = modifier)
            }
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                OvenHeatModeDropdown(
                    dropdownHeatModeStateHolder = dropdownHeatModeStateHolder,
                    modifier = modifier
                )
                OvenConvectionModeDropdown(
                    dropdownConvectionModeStateHolder = dropdownConvectionModeStateHolder,
                    modifier = modifier
                )
                OvenGrillModeDropdown(
                    dropdownGrillModeStateHolder = dropdownGrillModeStateHolder,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun OvenState(
    device: OvenDevice = viewModel(),
    isOn: Boolean,
    modifier: Modifier
) {
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
            loading = device.isLoading()
        )
    }
}

@Composable
fun OvenTemperature(
    device: OvenDevice = viewModel(),
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
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.OI_temperature),
                style = TextStyle(fontSize = 16.sp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${localTemperature}°C",
                    modifier = Modifier.padding(end = 4.dp)
                )
                Slider(value = localTemperature.toFloat(),
                    onValueChange = { localTemperature = it.toInt() },
                    valueRange = 90f..220f,
                    steps = 0,
                    enabled = !device.isLoading(),
                    modifier = Modifier.width(125.dp),
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
fun OvenHeatModeDropdown(
    dropdownHeatModeStateHolder: DropdownSelectorStateHolder,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldDropdownSelector(stateHolder = dropdownHeatModeStateHolder)
    }
}

@Composable
fun OvenConvectionModeDropdown(
    dropdownConvectionModeStateHolder: DropdownSelectorStateHolder,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldDropdownSelector(stateHolder = dropdownConvectionModeStateHolder)
    }
}

@Composable
fun OvenGrillModeDropdown(
    dropdownGrillModeStateHolder: DropdownSelectorStateHolder,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center
    ) {
        TextFieldDropdownSelector(stateHolder = dropdownGrillModeStateHolder)
    }
}
