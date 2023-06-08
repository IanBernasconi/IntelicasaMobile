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
import com.example.intelicasamobile.model.OvenConvectionMode
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenGrillMode
import com.example.intelicasamobile.model.OvenHeatMode
import com.example.intelicasamobile.ui.components.DropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun OvenDeviceInfoPreview() {
    val device = OvenDevice()
    IntelicasaMobileTheme {
        OvenDeviceInfo(
            isOn = device.state.isOn,
            temperature = device.state.temperature,
            convectionMode = device.state.convectionMode,
            grillMode = device.state.grillMode,
            heatMode = device.state.heatMode,
            setIsOn = { },
            setTemperature = { },
            setConvectionMode = { },
            setGrillMode = { },
            setHeatMode = { },

            modifier = Modifier,
            disabled = false,
            loading = false,

            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OvenDeviceInfo(
    isOn: Boolean,
    temperature: Int,
    convectionMode: OvenConvectionMode,
    grillMode: OvenGrillMode,
    heatMode: OvenHeatMode,
    setIsOn: (Boolean) -> Unit,
    setTemperature: (Int) -> Unit,
    setConvectionMode: (OvenConvectionMode) -> Unit,
    setGrillMode: (OvenGrillMode) -> Unit,
    setHeatMode: (OvenHeatMode) -> Unit,


    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localTemperature by remember { mutableStateOf(temperature) }
    var localConvectionMode by remember { mutableStateOf(convectionMode) }
    var localGrillMode by remember { mutableStateOf(grillMode) }
    var localHeatMode by remember { mutableStateOf(heatMode) }

    val dropdownHeatModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenHeatMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it.value,
                    icon = it.imageResourceId
                )
            },
            label = stringResource(id = R.string.OI_heat_mode),
            onItemSelected = { localHeatMode = it.value as OvenHeatMode; setHeatMode(it.value) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = localHeatMode.nameResId),
                value = localHeatMode,
                icon = localHeatMode.imageResourceId
            )
        )

    val dropdownConvectionModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenHeatMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it.value,
                    icon = it.imageResourceId
                )
            }, label = stringResource(id = R.string.OI_convection_mode),
            onItemSelected = {
                localConvectionMode = it.value as OvenConvectionMode; setConvectionMode(it.value)
            }, initialItem = DropdownSelectorItem(
                label = stringResource(id = localConvectionMode.nameResId),
                value = localConvectionMode,
                icon = localConvectionMode.imageResourceId
            )
        )

    val dropdownGrillModeStateHolder =
        rememberDropdownSelectorState(
            items = OvenHeatMode.values().map {
                DropdownSelectorItem(
                    label = stringResource(id = it.nameResId),
                    value = it.value,
                    icon = it.imageResourceId
                )
            },
            label = stringResource(id = R.string.OI_grill_mode),
            onItemSelected = { localGrillMode = it.value as OvenGrillMode; setGrillMode(it.value) },
            initialItem = DropdownSelectorItem(
                label = stringResource(id = localGrillMode.nameResId),
                value = localGrillMode,
                icon = localGrillMode.imageResourceId
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
                StateInfo(isOn = isOn, setIsOn = setIsOn, disabled = disabled, loading = loading)
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
                        text = stringResource(id = R.string.temperature),
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
                            onValueChangeFinished = { setTemperature(localTemperature) })
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