package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(name = "DeviceInfo")
@Composable
fun DeviceInfoPreview() {
    //DeviceInfo(device = MainUiState().devices[1])
}

@Preview
@Composable
fun DeviceInfoModalPreview() {
    //  DeviceInfoModal(device = MainUiState().devices[0], showDialog = true, onDismiss = {})
}

@Composable
fun DeviceInfoModal(
    device: Device,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    roomsViewModel: RoomsViewModel = viewModel(),
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = modifier.background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
            ) {
                DeviceInfo(
                    device = device,
                    modifier = modifier,
                    roomsViewModel = roomsViewModel,
                )
            }
        }
    }
}

@Composable
fun DeviceInfo(
    device: Device,
    modifier: Modifier = Modifier,
    roomsViewModel: RoomsViewModel = viewModel(),
) {
    IntelicasaMobileTheme() {
        Card(modifier = modifier) {
            Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                Column {
                    DeviceInfoHeader(device = device)
                    when (device) {
                        is LightDevice -> LightDeviceInfo(
                            device = device,
                        )

                        is ACDevice -> {
                            ACDeviceInfo(
                                device = device,
                            )
                        }

                        is VacuumDevice -> VacuumDeviceInfo(
                            device = device,
                            roomsViewModel = roomsViewModel
                        )

                        is OvenDevice -> OvenDeviceInfo(
                            device = device,
                        )

                        is DoorDevice -> DoorDeviceInfo(
                            device = device,
                        )
                    }
                }
            }
        }
    }
}

