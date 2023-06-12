package com.example.intelicasamobile.model

data class Room (
    val id: String,
    val roomType: RoomType,
    val name: String,
    val nameId: Int? = null
){
    private val devicesId: MutableList<String> = mutableListOf()

    fun setDevices(devices: List<String>){
        devicesId.addAll(devices)
    }

    fun containsDevice(id: String): Boolean{
        return devicesId.contains(id)
    }

}