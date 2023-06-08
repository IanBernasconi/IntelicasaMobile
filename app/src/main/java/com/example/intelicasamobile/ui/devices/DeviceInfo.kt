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
                            setBrightness = { device.state.setBrightness(it) },
                            color = device.state.color,
                            setColor = { device.state.setColor(it) },
                            isOn = device.state.isOn,
                            setIsOn = { device.state.setIsOn(it) }
                        )

                        is ACDevice -> ACDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { device.state.setTemperature(it) },
                            isOn = device.state.isOn, setIsOn = { device.state.setIsOn(it) },
                            mode = device.state.mode, setMode = { device.state.setMode(it)},
                            fanSpeed = device.state.fanSpeed, setFanSpeed = { device.state.setFanSpeed(it) },
                            verticalSwing = device.state.verticalSwing, setVerticalSwing = { device.state.setVerticalSwing(it) },
                            horizontalSwing = device.state.horizontalSwing, setHorizontalSwing = { device.state.setHorizontalSwing(it) }
                        )

                        is VacuumDevice -> VacuumDeviceInfo(
                            batteryPercentage = device.state.batteryPerc,
                            state = device.state.state, setState = { device.state.setState(it) },
                            mode = device.state.mode, setMode = { device.state.setMode(it) },
                            location = device.state.location, setLocation = { device.state.setLocation(it) },
                        )

                        is OvenDevice -> OvenDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { device.state.setTemperature(it) },
                            isOn = device.state.isOn, setIsOn = { device.state.setIsOn(it) },
                            heatMode = device.state.heatMode, setHeatMode = { device.state.setHeatMode(it) },
                            grillMode = device.state.grillMode, setGrillMode = { device.state.setGrillMode(it) },
                            convectionMode = device.state.convectionMode, setConvectionMode = { device.state.setConvectionMode(it) }
                        )

                        is DoorDevice -> DoorDeviceInfo(
                            isLocked = device.state.isLocked, setIsLocked = { device.state.setLocked(it) },
                            isOpen = device.state.isOpen, setIsOpen = { device.state.setOpen(it) }
                        )
                    }
                }
            }
        }
    }
}

