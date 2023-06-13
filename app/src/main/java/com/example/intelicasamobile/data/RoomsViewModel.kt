package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.data.network.RetrofitClient
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
                println("Response: $response")
                val rooms = getRoomsFromNetwork(response.body())
                println("Rooms: $rooms")
                _roomsUiState.update { it.copy(
                    rooms = rooms?: emptyList(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                _roomsUiState.update { it.copy(
                    isLoading = false,
                    message = e.message
                ) }
            }
        }
    }

    private fun getRoomsFromNetwork(networkRoomsList: NetworkRoomsList?): List<Room>? {
        return networkRoomsList?.result?.let { array ->
            (0 until array.size).map{ index ->
                val networkRoom = array[index]
                Room(
                    id = networkRoom.id?:"",
                    name = networkRoom.name?:"",
                    roomType = RoomType.getRoomType(networkRoom.meta?.type ?: RoomType.OTHER.name),
                    meta = RoomMeta((networkRoom.meta?.type ?: RoomType.OTHER.name))
                )
            }
        }
    }

    fun getRoomById(roomId: String): Room? {
        return _roomsUiState.value.rooms.find { it.id == roomId }
    }

    fun setCurrentRoom(room: Room) {
        _roomsUiState.update { it.copy(currentRoom = room) }
    }
}



