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

    val routines = listOf(
        Routine(R.string.routine1, emptyList()),
        Routine(R.string.routine1, emptyList()),
    )

    val rooms = listOf(
        Room(
            roomType = RoomType.LIVINGROOM,
            name = "Living Room",
            devices = emptyList()
        ),
        Room(
            roomType = RoomType.KITCHEN,
            name = "Kitchen",
            devices = emptyList()
        ),
        Room(
            roomType = RoomType.BATHROOM,
            name = "Bathroom",
            devices = emptyList()
        ),
        Room(
            roomType = RoomType.GARDEN,
            name = "Garden",
            devices = emptyList()
        ),

        )
}