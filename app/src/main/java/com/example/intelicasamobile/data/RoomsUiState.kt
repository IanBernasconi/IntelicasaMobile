package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Room

data class RoomsUiState(
    val rooms: List<Room>,
    val currentRoom: Room? = null,
)