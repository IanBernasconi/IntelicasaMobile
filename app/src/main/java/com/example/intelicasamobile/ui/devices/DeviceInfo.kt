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
                            brightness = device.state.brightness,
                            setBrightness = { device.setBrightness(it) },
                            color = device.state.color,
                            setColor = { device.setColor(it) },
                            isOn = device.state.isOn,
                            setIsOn = { device.setIsOn(it) }
                        )

                        is ACDevice -> ACDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { device.setTemperature(it) },
                            isOn = device.state.isOn, setIsOn = { device.setIsOn(it) },
                            mode = device.state.mode, setMode = { device.setMode(it)},
                            fanSpeed = device.state.fanSpeed, setFanSpeed = { device.setFanSpeed(it) },
                            verticalSwing = device.state.verticalSwing, setVerticalSwing = { device.setVerticalSwing(it) },
                            horizontalSwing = device.state.horizontalSwing, setHorizontalSwing = { device.setHorizontalSwing(it) }
                        )

                        is VacuumDevice -> VacuumDeviceInfo(
                            batteryPercentage = device.state.batteryPerc,
                            state = device.state.state, setState = { device.setState(it) },
                            mode = device.state.mode, setMode = { device.setMode(it) },
                            location = device.state.location, setLocation = { device.setLocation(it) },
                        )

                        is OvenDevice -> OvenDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { device.setTemperature(it) },
                            isOn = device.state.isOn, setIsOn = { device.setIsOn(it) },
                            heatMode = device.state.heatMode, setHeatMode = { device.setHeatMode(it) },
                            grillMode = device.state.grillMode, setGrillMode = { device.setGrillMode(it) },
                            convectionMode = device.state.convectionMode, setConvectionMode = { device.setConvectionMode(it) }
                        )

                        is DoorDevice -> DoorDeviceInfo(
                            isLocked = device.state.isLocked, setIsLocked = { device.setLocked(it) },
                            isOpen = device.state.isOpen, setIsOpen = { device.setOpen(it) }
                        )
                    }
                }
            }
        }
    }
}

