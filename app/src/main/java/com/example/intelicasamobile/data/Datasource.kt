package com.example.intelicasamobile.data

import com.example.intelicasamobile.data.network.data.DeviceApi.getAll
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomType
import com.example.intelicasamobile.model.Routine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object Datasource {
//    val devices = listOf(
//        LightDevice(LightState(brightness = 1, color = Color.Red)),
//        ACDevice(ACState(temperature = 20f)),
//        OvenDevice(),
//        VacuumDevice(),
//        DoorDevice(),
//        LightDevice(),
//        ACDevice(),
//        OvenDevice(),
//        VacuumDevice(),
//        DoorDevice(),
//        OvenDevice(),
//        VacuumDevice(),
//        DoorDevice()
//    )


}