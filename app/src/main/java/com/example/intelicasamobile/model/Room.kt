package com.example.intelicasamobile.model

data class Room (
    val id: String,
    val roomType: RoomType,
    val name: String,
    val nameId: Int? = null,
)