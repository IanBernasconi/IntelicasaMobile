package com.example.intelicasamobile.data

import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceTypes
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.LightState
import com.example.intelicasamobile.model.Meta
import com.example.intelicasamobile.model.Routine

object Datasource {
    val devices = listOf(
        LightDevice(state = LightState(brightness = 1, color = Color.Red)),
        ACDevice(state = ACState(temperature = 20f)),
        Device(deviceType = DeviceTypes.OVEN, R.string.oven),
        Device(deviceType = DeviceTypes.VACUUM_CLEANER, R.string.vacuum_cleaner),
        Device(deviceType = DeviceTypes.DOOR, R.string.door),
        Device(deviceType = DeviceTypes.LAMP, R.string.lamp),
        Device(deviceType = DeviceTypes.AIR_CONDITIONER, R.string.air_conditioner),
        Device(deviceType = DeviceTypes.OVEN, R.string.oven),
        Device(deviceType = DeviceTypes.VACUUM_CLEANER, R.string.vacuum_cleaner),
        Device(deviceType = DeviceTypes.DOOR, R.string.door),
    )


    val routines = listOf(
        Routine(R.string.routine1, List(2) { DeviceTypes.LAMP;DeviceTypes.DOOR}),
        Routine(R.string.routine1, List(3) { DeviceTypes.LAMP;DeviceTypes.LAMP; DeviceTypes.DOOR}),
        )
}