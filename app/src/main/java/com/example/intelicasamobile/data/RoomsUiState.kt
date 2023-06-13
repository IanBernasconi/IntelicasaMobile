package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.Routine

data class RoomsUiState (
    val rooms: List<Room>,
    val isLoading: Boolean = false,
    val message: String? = null,
    val currentRoom: Room? = null,
)

val RoomsUiState.hasError : Boolean
    get() = message != null