package com.example.intelicasamobile.ui.devices

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.intelicasamobile.model.OvenConvectionMode
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenGrillMode
import com.example.intelicasamobile.model.OvenHeatMode
import com.example.intelicasamobile.ui.components.TextFieldDropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun OvenDeviceInfoPreview() {
    val device = OvenDevice(deviceId = "1", deviceName = "Horno")
    IntelicasaMobileTheme {
        OvenDeviceInfo(
            state = device,
            modifier = Modifier,
            disabled = false,
            loading = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OvenDeviceInfo(
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
    state: OvenDevice = viewModel(),
) {

    val uiState by state.state.collectAsState()

    var localTemperature by remember { mutableStateOf(uiState.temperature) }

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
            onItemSelected = { state.setHeatMode(it.value as OvenHeatMode) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = uiState.heatMode.nameResId),
                value = uiState.heatMode,
                icon = uiState.heatMode.imageResourceId
            )
        )

    val dropdownConvectionModeStateHolder =
        rememberDropdownSelectorState(items = OvenConvectionMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.OI_convection_mode), onItemSelected = {
            state.setConvectionMode(it.value as OvenConvectionMode)
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
            onItemSelected = { state.setGrillMode(it.value as OvenGrillMode) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = uiState.grillMode.nameResId),
                value = uiState.grillMode,
                icon = uiState.grillMode.imageResourceId
            )
        )


    IntelicasaMobileTheme {
        Column(modifier = modifier) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StateInfo(
                    isOn = uiState.isOn,
                    setIsOn = { state.setIsOn(it) },
                    disabled = disabled,
                    loading = loading
                )
            }


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
                            text = "${Math.floor(localTemperature.toDouble())}°C",
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Slider(value = localTemperature.toFloat(),
                            onValueChange = { localTemperature = it.toInt() },
                            valueRange = 90f..220f,
                            steps = 1,
                            enabled = !(disabled || loading),
                            modifier = Modifier.width(125.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.secondary
                            ),
                            onValueChangeFinished = { state.setTemperature(localTemperature) })
                    }
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.Center
            ) {
                TextFieldDropdownSelector(stateHolder = dropdownHeatModeStateHolder)
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.Center
            ) {
                TextFieldDropdownSelector(stateHolder = dropdownConvectionModeStateHolder)
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.Center
            ) {
                TextFieldDropdownSelector(stateHolder = dropdownGrillModeStateHolder)
            }
        }
    }
}