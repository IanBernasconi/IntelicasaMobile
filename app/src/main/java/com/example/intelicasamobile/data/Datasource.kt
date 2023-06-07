package com.example.intelicasamobile.data

import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceTypes
import com.example.intelicasamobile.model.Routine

object Datasource {
    val devices = listOf(
        Device(deviceType = DeviceTypes.LAMP, R.string.lamp),
        Device(deviceType = DeviceTypes.AIR_CONDITIONER, R.string.air_conditioner),
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
        Routine(R.string.rutine1),
        Routine(R.string.rutine1),
        )
}