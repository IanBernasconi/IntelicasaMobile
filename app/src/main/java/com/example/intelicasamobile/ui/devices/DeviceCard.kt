package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Battery0Bar
import androidx.compose.material.icons.outlined.Battery1Bar
import androidx.compose.material.icons.outlined.Battery2Bar
import androidx.compose.material.icons.outlined.Battery3Bar
import androidx.compose.material.icons.outlined.Battery4Bar
import androidx.compose.material.icons.outlined.Battery5Bar
import androidx.compose.material.icons.outlined.Battery6Bar
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.BatteryFull
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.model.VacuumStateEnum

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    //DeviceCard(device = Device(DeviceTypes.OVEN, R.string.oven))
}

@Composable
fun DeviceCard(
    device: Device,
    modifier: Modifier = Modifier,
    roomsViewModel: RoomsViewModel = viewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    val devicesModel by remember { mutableStateOf(DevicesViewModel.getInstance()) }
    val devicesState by devicesModel.devicesUiState.collectAsState()
    Card(elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
        modifier = modifier.clickable { showDialog = true }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),

                ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_small),
                            bottom = dimensionResource(id = R.dimen.padding_small)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = device.deviceType.imageResourceId),
                        contentDescription = "",
                        modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
                    )
                    when (device) {
                        is ACDevice -> {
                            val acState by device.state.collectAsState()
                            IconButton(
                                onClick = { device.setIsOn(!acState.isOn) }, enabled = !device.isLoading()
                            ) {
                                Image(
                                    painter = painterResource(id = if (acState.isOn) R.drawable.poweron else R.drawable.poweroff),
                                    contentDescription = stringResource(R.string.power),
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small_size))
                                )
                            }
                        }

                        is LightDevice -> {
                            val lightState by device.state.collectAsState()
                            IconButton(
                                onClick = { device.setIsOn(!lightState.isOn) }, enabled = !device.isLoading()
                            ) {
                                Image(
                                    painter = painterResource(id = if (lightState.isOn) R.drawable.poweron else R.drawable.poweroff),
                                    contentDescription = stringResource(id = R.string.power),
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small_size))
                                )
                            }
                        }

                        is OvenDevice -> {
                            val ovenState by device.state.collectAsState()
                            IconButton(
                                onClick = { device.setIsOn(!ovenState.isOn) }, enabled = !device.isLoading()
                            ) {
                                Image(
                                    painter = painterResource(id = if (ovenState.isOn) R.drawable.poweron else R.drawable.poweroff),
                                    contentDescription = stringResource(id = R.string.power),
                                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small_size))
                                )
                            }
                        }

                        is VacuumDevice -> {
                            val vacuumState by device.state.collectAsState()
                            Icon(
                                imageVector = if (vacuumState.state != VacuumStateEnum.CHARGING) when (vacuumState.batteryLevel) {
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
                                contentDescription = "BatteryLevel",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small_size))
                            )
                        }

                        is DoorDevice -> {
                            val doorState by device.state.collectAsState()
                            Icon(
                                painter = painterResource(
                                    id = if (doorState.isOpen) R.drawable.door_open
                                    else if (doorState.isLocked) R.drawable.door_closed_lock
                                    else R.drawable.door_closed
                                ),
                                contentDescription = stringResource(id = R.string.door),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_small_size))
                            )

                        }
                    }
                }
                Row(
                    modifier = modifier, verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = device.name,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                    )
                }
            }
        }
        DeviceInfoModal(
            device = device,
            showDialog = showDialog,
            onDismiss = {
                showDialog = false

                if(devicesState.stateModified){
                    devicesModel.showSnackBar()
                    devicesModel.resetStateModified()
                }
            },
            roomsViewModel = roomsViewModel,
        )
    }
}