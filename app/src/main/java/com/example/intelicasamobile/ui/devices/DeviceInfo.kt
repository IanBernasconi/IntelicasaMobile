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
                            brightness = device.state.brightness, setBrightness = { },
                            color = device.state.color, setColor = { },
                            isOn = device.state.isOn, setIsOn = { }
                        )
                        is ACDevice -> ACDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { },
                            isOn = device.state.isOn, setIsOn = { },
                            mode = device.state.mode, setMode = { },
                            fanSpeed = device.state.fanSpeed, setFanSpeed = { },
                            verticalSwing = device.state.verticalSwing, setVerticalSwing = { },
                            horizontalSwing = device.state.horizontalSwing, setHorizontalSwing = { }
                        )

                        is VacuumDevice -> VacuumDeviceInfo(
                            batteryPercentage = device.state.batteryPerc,
                            state = device.state.state, setState = { },
                            mode = device.state.mode, setMode = { },
                            location = device.state.location, setLocation = { },
                        )
                        is OvenDevice -> OvenDeviceInfo(
                            temperature = device.state.temperature, setTemperature = { },
                            isOn = device.state.isOn, setIsOn = { },
                            heatMode = device.state.heatMode, setHeatMode = { },
                            grillMode = device.state.grillMode, setGrillMode = { },
                            convectionMode = device.state.convectionMode, setConvectionMode = { }
                        )
                        is DoorDevice -> DoorDeviceInfo(
                            isLocked = device.state.isLocked, setIsLocked = { },
                            isOpen = device.state.isOpen, setIsOpen = { }
                        )
                    }
                }
            }
        }
    }
}

