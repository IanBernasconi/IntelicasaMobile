package com.example.intelicasamobile.model

import androidx.lifecycle.ViewModel
import com.example.intelicasamobile.data.Datasource
import com.example.intelicasamobile.ui.components.DropdownSelectorStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Room (
    val roomType: RoomType,
    val name: String,
    val devices: List<Device>
)

data class RoomScreenState(
    private val room: Room = Datasource.rooms[0],
) : ViewModel()
 {
    private val _state: MutableStateFlow<Room> = MutableStateFlow(room)
    val state: StateFlow<Room> = _state.asStateFlow()

    fun setRoom(room: Room) {
        _state.value = room
    }

}

class RoomsScreen(
    val room: Room,
    val dropdownRoomStateHolder: DropdownSelectorStateHolder,
    val index: Int,
)