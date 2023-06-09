package com.example.intelicasamobile.data

import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceTypes
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.LightState
import com.example.intelicasamobile.model.Meta
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenState
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.model.VacuumState

object Datasource {
    val devices = listOf(
        LightDevice(LightState(brightness = 1, color = Color.Red)),
        ACDevice(ACState(temperature = 20f)),
        OvenDevice(),
        VacuumDevice(),
        DoorDevice(),
        LightDevice(),
        ACDevice(),
        OvenDevice(),
        VacuumDevice(),
        DoorDevice(),
        OvenDevice(),
        VacuumDevice(),
        DoorDevice()
    )

    val routines = listOf(
        Routine(R.string.routine1, devices.subList(0, 3)),
        Routine(R.string.routine1, devices.subList(3, 6)),
    )
}