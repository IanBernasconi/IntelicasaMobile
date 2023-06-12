package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.network.data.RoomApi
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoomsViewModel : ViewModel() {
    private val _roomsUiState = MutableStateFlow(RoomsUiState(emptyList()))
    val roomsUiState: StateFlow<RoomsUiState> = _roomsUiState.asStateFlow()

    private val roomsMutex = Mutex()

    suspend fun getRooms(refresh: Boolean = true): List<Room> {
        if (refresh || roomsUiState.value.rooms.isEmpty()) {
            roomsMutex.withLock {
                val updatedRooms = mutableListOf<Room>()
                updatedRooms.addAll(roomsUiState.value.rooms)

                RoomApi.getAll().forEach { room ->
                    val index = updatedRooms.indexOfFirst { it.id == room.id }
                    if (index != -1) {
                        updatedRooms[index] = room
                    } else {
                        updatedRooms.add(room)
                    }
                }

                if (updatedRooms.indexOfFirst { it.id == "all" } == -1) {
                    updatedRooms.add(Room("all", RoomType.OTHER, name = "", nameId = R.string.all_devices_room))
                }

                _roomsUiState.update { it.copy(rooms = updatedRooms) }
                _roomsUiState.update { it.copy(currentRoom = updatedRooms[updatedRooms.size - 1]) }
            }
        }

        return roomsMutex.withLock { roomsUiState.value.rooms }
    }

    fun setCurrentRoom(room: Room) {
        _roomsUiState.update { it.copy(currentRoom = room) }
    }
}