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
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACMode
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.ui.components.DropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import kotlin.math.floor

@Preview(showBackground = true)
@Composable
fun ACDeviceInfoPreview() {
    val device = ACDevice()
    IntelicasaMobileTheme {
        ACDeviceInfo(
            state = device,
            modifier = Modifier,
            disabled = false,
            loading = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ACDeviceInfo(
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
    state: ACDevice = viewModel(),
) {

    val uiState by state.state.collectAsState()

    var localTemperature by remember { mutableStateOf(uiState.temperature) }
    var localFanSpeed by remember { mutableStateOf(uiState.fanSpeed) }
    var localVerticalSwing by remember { mutableStateOf(uiState.verticalSwing) }
    var localHorizontalSwing by remember { mutableStateOf(uiState.horizontalSwing) }

    val dropdownModeStateHolder = rememberDropdownSelectorState(items = ACMode.values().map {
        DropdownSelectorItem(
            label = stringResource(id = it.nameResId),
            value = it,
            icon = it.imageResourceId
        )
    }, label = stringResource(id = R.string.mode),
        initialItem = DropdownSelectorItem(
            label = stringResource(id = uiState.mode.nameResId),
            value = uiState.mode,
            icon = uiState.mode.imageResourceId
    ), onItemSelected = { state.setMode(it.value as ACMode) })

    IntelicasaMobileTheme {
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StateInfo(isOn = uiState.isOn, setIsOn = { state.setIsOn(it) })
            }

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
                            text = "${floor(localTemperature.toDouble())}°C",
                            modifier = modifier.padding(end = 4.dp)
                        )
                        Slider(value = localTemperature,
                            onValueChange = { localTemperature = it },
                            valueRange = 18f..38f,
                            steps = 0,
                            enabled = !(disabled || loading),
                            modifier = modifier.width(125.dp),
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
                DropdownSelector(stateHolder = dropdownModeStateHolder)
            }

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
                            enabled = !disabled && !loading,
                            onValueChangeFinished = { state.setVerticalSwing(localVerticalSwing) },
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
                            enabled = !disabled && !loading,
                            onValueChangeFinished = { state.setHorizontalSwing(localHorizontalSwing) },
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
                                id = R.string.auto_mode), modifier = modifier.padding(end = 4.dp)
                        )
                        Slider(
                            value = localFanSpeed.toFloat(),
                            onValueChange = { localFanSpeed = floor(it).toInt() },
                            enabled = !disabled && !loading,
                            onValueChangeFinished = { state.setFanSpeed(localFanSpeed) },
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
    }

}