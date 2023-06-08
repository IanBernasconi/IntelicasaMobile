package com.example.intelicasamobile.ui.devices

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Battery0Bar
import androidx.compose.material.icons.outlined.Battery1Bar
import androidx.compose.material.icons.outlined.Battery2Bar
import androidx.compose.material.icons.outlined.Battery3Bar
import androidx.compose.material.icons.outlined.Battery4Bar
import androidx.compose.material.icons.outlined.Battery5Bar
import androidx.compose.material.icons.outlined.Battery6Bar
import androidx.compose.material.icons.outlined.BatteryAlert
import androidx.compose.material.icons.outlined.BatteryFull
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.VacuumCleanMode
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.model.VacuumStateEnum
import com.example.intelicasamobile.ui.components.DropdownSelector
import com.example.intelicasamobile.ui.components.DropdownSelectorItem
import com.example.intelicasamobile.ui.components.rememberDropdownSelectorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun VacummDeviceInfoPreview() {
    val device = VacuumDevice()
    IntelicasaMobileTheme {
        VacuumDeviceInfo(
            batteryPercentage = device.state.batteryPerc,
            state = device.state.state,
            setState = {},
            mode = device.state.mode,
            setMode = {},
            location = device.state.location,
            setLocation = {},
            modifier = Modifier,
            disabled = false,
            loading = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacuumDeviceInfo(
    batteryPercentage: Int,
    state: VacuumStateEnum,
    setState: (VacuumStateEnum) -> Unit,
    mode: VacuumCleanMode,
    setMode: (VacuumCleanMode) -> Unit,
    location: String,
    setLocation: (String) -> Unit,

    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localState by remember { mutableStateOf(state) }
    var localMode by remember { mutableStateOf(mode) }
    var localLocation by remember { mutableStateOf(location) }

    val dropdownModeStateHolder = rememberDropdownSelectorState(
        items = VacuumCleanMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it.value,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.mode),
        onItemSelected = { localMode = it.value as VacuumCleanMode; setMode(localMode) },
        initialItem = DropdownSelectorItem(
            label = stringResource(id = localMode.nameResId),
            value = localMode.value,
            icon = localMode.imageResourceId
        )
    )

    val dropdownLocationStateHolder = rememberDropdownSelectorState(
        items = VacuumCleanMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it.value,
                icon = it.imageResourceId
            )
        }, label = stringResource(id = R.string.room),
        onItemSelected = { localLocation = it.value as String; setLocation(localLocation) },
        initialItem = DropdownSelectorItem(
            label = localLocation,
            value = localLocation,
            icon = null
        )
    )

    IntelicasaMobileTheme {
        Surface(
            color = Color.White
        ) {
            Column(modifier = modifier) {

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
                            text = stringResource(id = localState.nameResId),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }

                    Row(modifier = Modifier) {
                        Icon(
                            imageVector = when (batteryPercentage) {
                                in 0..5 -> Icons.Outlined.Battery0Bar
                                in 6..15 -> Icons.Outlined.Battery1Bar
                                in 15..30 -> Icons.Outlined.Battery2Bar
                                in 31..45 -> Icons.Outlined.Battery3Bar
                                in 45..60 -> Icons.Outlined.Battery4Bar
                                in 61..75 -> Icons.Outlined.Battery5Bar
                                in 75..90 -> Icons.Outlined.Battery6Bar
                                else -> Icons.Outlined.BatteryFull
                            }, contentDescription = null, modifier = Modifier.width(24.dp)
                        )
                        Text(
                            text = "${batteryPercentage}%", style = TextStyle(fontSize = 16.sp)
                        )
                    }
                }

                if (localLocation != "") {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
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
                            onClick = { setState(VacuumStateEnum.CLEANING); localState = VacuumStateEnum.CLEANING },
                            elevation = ButtonDefaults.buttonElevation(
                                20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                            ),
                            shape = RoundedCornerShape(5.dp),
                            enabled = !disabled && !loading && localState != VacuumStateEnum.CLEANING
                        ) {
                            if (batteryPercentage > 5) {
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
                            onClick = { setState(VacuumStateEnum.PAUSED); localState = VacuumStateEnum.PAUSED },
                            elevation = ButtonDefaults.buttonElevation(
                                20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                            ),
                            shape = RoundedCornerShape(5.dp),
                            enabled = !disabled && !loading && localState == VacuumStateEnum.CLEANING
                        ) {
                            Text(text = stringResource(id = R.string.VCA_pause))
                        }
                        Button(
                            onClick = { setState(VacuumStateEnum.CHARGING); localState = VacuumStateEnum.CHARGING },
                            elevation = ButtonDefaults.buttonElevation(
                                20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                            ),
                            shape = RoundedCornerShape(5.dp),
                            enabled = !disabled && !loading && localState != VacuumStateEnum.CHARGING
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
                    DropdownSelector(stateHolder = dropdownModeStateHolder)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DropdownSelector(stateHolder = dropdownLocationStateHolder)
                }
            }
        }
    }
}