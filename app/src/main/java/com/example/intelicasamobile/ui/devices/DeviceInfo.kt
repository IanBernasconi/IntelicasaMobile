package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.data.MainUiState
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
    DeviceInfo(device = MainUiState().devices[1])
}

@Composable
fun DeviceInfo(
    device: Device,
    modifier: Modifier = Modifier
) {
    IntelicasaMobileTheme() {
        Card(modifier = modifier) {
            Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                Column {
                    DeviceInfoHeader(device = device, onDelete = {})
                    when (device) {
                        is LightDevice -> LightDeviceInfo(
                            state = device
                        )

                        is ACDevice -> ACDeviceInfo(
                            state = device
                        )

                        is VacuumDevice -> VacuumDeviceInfo(
                            state = device
                        )

                        is OvenDevice -> OvenDeviceInfo(
                            state = device
                        )

                        is DoorDevice -> DoorDeviceInfo(
                            state  = device
                        )
                    }
                }
            }
        }
    }
}

