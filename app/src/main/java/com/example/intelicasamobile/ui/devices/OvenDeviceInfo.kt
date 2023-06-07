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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACMode
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenHeatMode
import com.example.intelicasamobile.ui.components.DropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun OvenDeviceInfoPreview() {
    IntelicasaMobileTheme {
        OvenDeviceInfo(
            device = OvenDevice(),
            modifier = Modifier,
            disabled = false,
            loading = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OvenDeviceInfo(
    device: OvenDevice,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var temperature by remember { mutableStateOf(device.state.temperature) }
    var convectionMode by remember { mutableStateOf(device.state.convectionMode) }
    var grillMode by remember { mutableStateOf(device.state.grillMode) }
    var heatMode by remember { mutableStateOf(device.state.heatMode) }

    val dropdownHeatModeStateHolder = rememberDropdownSelectorState(
        items = OvenHeatMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it.value,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.OI_heat_mode)
    )

    val dropdownConvectionModeStateHolder = rememberDropdownSelectorState(
        items = OvenHeatMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it.value,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.OI_convection_mode)
    )

    val dropdownGrillModeStateHolder = rememberDropdownSelectorState(
        items = OvenHeatMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it.value,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.OI_grill_mode)
    )


    IntelicasaMobileTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(modifier = modifier) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    StateInfo(isOn = device.state.isOn)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.temperature),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Column(modifier = Modifier) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${floor(temperature)}°C",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Slider(
                                value = temperature,
                                onValueChange = { value -> temperature = value },
                                valueRange = 90f..230f,
                                steps = 1,
                                enabled = !(disabled || loading),
                                modifier = Modifier.width(150.dp),
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colorScheme.secondary,
                                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.background
                                ),
                                onValueChangeFinished = {} //{ setTemperature() }
                            )
                        }
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DropdownSelector(stateHolder = dropdownHeatModeStateHolder)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DropdownSelector(stateHolder = dropdownConvectionModeStateHolder)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DropdownSelector(stateHolder = dropdownGrillModeStateHolder)
                }
            }
        }
    }
}