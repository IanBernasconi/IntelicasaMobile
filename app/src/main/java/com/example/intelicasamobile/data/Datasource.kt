package com.example.intelicasamobile.data

import Api.Companion.get
import DeviceApi.getAll
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.LightState
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomType
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.model.VacuumDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val devices = listOf<Device>()

    private val devicesMutex = Mutex()

    suspend fun getDevices(refresh: Boolean = false): List<Device> {
        if (refresh || devices.isEmpty()) {
            devicesMutex.withLock {
                val devices = getAll()
                devices?.forEach { device ->
                    val index = devices.indexOfFirst { it.id == device.id }
                    if (index != -1) {
                        devices.toMutableList().apply { set(index, device) }
                    } else {
                        devices.toMutableList().apply { add(device) }
                    }
                }
            }
        }
        return devicesMutex.withLock { devices.toList() }
    }


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