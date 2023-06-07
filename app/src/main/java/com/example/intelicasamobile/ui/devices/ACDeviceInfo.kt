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
import com.example.intelicasamobile.ui.components.DropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun ACDeviceInfoPreview() {
    IntelicasaMobileTheme {
        ACDeviceInfo(
            device = ACDevice(),
            modifier = Modifier,
            disabled = false,
            loading = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ACDeviceInfo(
    device: ACDevice,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var temperature by remember { mutableStateOf(device.state.temperature) }
    var mode by remember { mutableStateOf(device.state.mode) }
    var fanSpeed by remember { mutableStateOf(device.state.fanSpeed) }
    var verticalSwing by remember { mutableStateOf(device.state.verticalSwing) }
    var horizontalSwing by remember { mutableStateOf(device.state.horizontalSwing) }

    val dropdownModeStateHolder = rememberDropdownSelectorState(
        items = ACMode.values().map {
            DropdownSelectorItem(
                label = it.name.lowercase().replaceFirstChar { char -> char.uppercase() },
                value = it,
                icon = it.imageResourceId
            )
        }, label = "Modo", initialItem =
        DropdownSelectorItem(
            label = mode.name.lowercase().replaceFirstChar { char -> char.uppercase() },
            value = mode,
            icon = mode.imageResourceId
        )
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
                                text = "${Math.floor(temperature.toDouble())}°C",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Slider(
                                value = temperature,
                                onValueChange = { value -> temperature = value },
                                valueRange = 18f..38f,
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
                    DropdownSelector(stateHolder = dropdownModeStateHolder)
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
                            text = stringResource(id = R.string.vertical_swing),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Column(modifier = Modifier) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (verticalSwing != 0) "${verticalSwing}°" else "Auto",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Slider(
                                value = verticalSwing.toFloat(),
                                onValueChange = { value ->
                                    verticalSwing =
                                        Math.floor(value.toDouble()).toInt()
                                },
                                enabled = !disabled && !loading,
                                onValueChangeFinished = { /*setVerticalSwing()*/ },
                                modifier = Modifier.width(150.dp),
                                valueRange = 0f..90f,
                                steps = 3,
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colorScheme.secondary,
                                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
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
                            text = stringResource(id = R.string.horizontal_swing),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = if (horizontalSwing != -135) "${horizontalSwing}°" else "Auto",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Slider(
                                value = horizontalSwing.toFloat(),
                                onValueChange = { value ->
                                    horizontalSwing =
                                        Math.floor(value.toDouble()).toInt()
                                },
                                enabled = !disabled && !loading,
                                onValueChangeFinished = { /*setHorizontalSwing()*/ },
                                modifier = Modifier.width(150.dp),
                                valueRange = 0f..90f,
                                steps = 3,
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colorScheme.secondary,
                                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
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
                            text = stringResource(id = R.string.fan_speed),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Column(modifier = Modifier) {
                        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${fanSpeed}°",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Slider(
                                value = fanSpeed.toFloat(),
                                onValueChange = { value ->
                                    fanSpeed = Math.floor(value.toDouble()).toInt()
                                },
                                enabled = !disabled && !loading,
                                onValueChangeFinished = { /*setFanSpeed()*/ },
                                modifier = Modifier.width(150.dp),
                                valueRange = 0f..5f,
                                steps = 5,
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colorScheme.secondary,
                                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}