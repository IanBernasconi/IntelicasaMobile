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
                            state = device.state,
                            setBrightness = { device.setBrightness(it) },
                            setColor = { device.setColor(it) },
                            setIsOn = { device.setIsOn(it) }
                        )

                        is ACDevice -> ACDeviceInfo(
                            state = device.state,
                            setTemperature = { device.setTemperature(it) },
                            setIsOn = { device.setIsOn(it) },
                            setMode = { device.setMode(it)},
                            setFanSpeed = { device.setFanSpeed(it) },
                            setVerticalSwing = { device.setVerticalSwing(it) },
                            setHorizontalSwing = { device.setHorizontalSwing(it) }
                        )

                        is VacuumDevice -> VacuumDeviceInfo(
                            state = device.state,
                            setState = { device.setState(it) },
                            setMode = { device.setMode(it) },
                            setLocation = { device.setLocation(it) },
                        )

                        is OvenDevice -> OvenDeviceInfo(
                            state = device.state,
                            setTemperature = { device.setTemperature(it) },
                            setIsOn = { device.setIsOn(it) },
                            setHeatMode = { device.setHeatMode(it) },
                            setGrillMode = { device.setGrillMode(it) },
                            setConvectionMode = { device.setConvectionMode(it) }
                        )

                        is DoorDevice -> DoorDeviceInfo(
                            state  = device.state,
                            setIsLocked = { device.setLocked(it) },
                            setIsOpen = { device.setOpen(it) }
                        )
                    }
                }
            }
        }
    }
}

