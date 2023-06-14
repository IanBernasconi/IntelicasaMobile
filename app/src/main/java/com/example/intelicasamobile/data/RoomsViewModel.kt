package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import com.example.intelicasamobile.data.network.model.NetworkRoomsList
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomMeta
import com.example.intelicasamobile.model.RoomType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RoomsViewModel : ViewModel() {
    private val _roomsUiState = MutableStateFlow(RoomsUiState(emptyList()))
    val roomsUiState: StateFlow<RoomsUiState> = _roomsUiState.asStateFlow()

    private var fetchJob: Job? = null

    fun dismissMessage() {
        _roomsUiState.update { it.copy(message = null) }
    }

    fun fetchRooms() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _roomsUiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getRooms()
            }.onSuccess { response ->
                var rooms = getRoomsFromNetwork(response.body())?.toMutableList()
                println("Rooms: $rooms")
                if (rooms == null) {
                    rooms = mutableListOf(
                        Room(
                            "all", RoomType.OTHER, name = "", nameId = R.string.all_devices_room
                        )
                    )
                } else {
                    if (rooms.indexOfFirst { it.id == "all" } == -1) {
                        rooms.add(
                            0, Room(
                                "all", RoomType.OTHER, name = "", nameId = R.string.all_devices_room
                            )
                        )
                    }
                }

                _roomsUiState.update {
                    it.copy(
                        rooms = rooms, currentRoom = rooms.firstOrNull()
                    )
                }
                roomsUiState.value.rooms.filter { it.id != "all" }.forEach {
                    fetchRoomDevices(it)
                    println("Room Fetch: $it")
                }
                _roomsUiState.update { it.copy(isLoading = false) }

            }.onFailure { e ->
                _roomsUiState.update {
                    it.copy(
                        isLoading = false, message = e.message
                    )
                }
            }
        }
    }

    private fun fetchRoomDevices(room: Room) {
        viewModelScope.launch {
            _roomsUiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getDevicesByRoom(room.id)
            }.onSuccess { response ->
                val devicesIds = getRoomDevicesFromNetwork(response.body())
                room.setDevices(devicesIds)
                _roomsUiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.onFailure { e ->
                _roomsUiState.update {
                    it.copy(
                        isLoading = false, message = e.message
                    )
                }
            }
        }

    }

    private fun getRoomDevicesFromNetwork(networkDeviceList: NetworkDeviceList?): List<String> {
        val devicesIds = mutableListOf<String>()
        networkDeviceList?.result?.let { array ->
            array.map { devResult ->
                devicesIds.add(devResult.id)
            }
        }
        println("Devices Ids: $devicesIds")
        return devicesIds
    }


    private fun getRoomsFromNetwork(networkRoomsList: NetworkRoomsList?): List<Room>? {
        return networkRoomsList?.result?.let { array ->
            array.map { networkRoom ->
                Room(
                    id = networkRoom.id ?: "",
                    name = networkRoom.name ?: "",
                    roomType = RoomType.getRoomType(networkRoom.meta?.type ?: RoomType.OTHER.name),
                    meta = RoomMeta((networkRoom.meta?.type ?: RoomType.OTHER.name))
                )
            }
        }
    }

    fun getRoomById(roomId: String): Room? {
        return roomsUiState.value.rooms.find { it.id == roomId }
    }

    fun setCurrentRoom(room: Room) {
        _roomsUiState.update { it.copy(currentRoom = room) }
    }
}



