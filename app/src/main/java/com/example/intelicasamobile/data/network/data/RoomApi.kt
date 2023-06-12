package com.example.intelicasamobile.data.network.data

import Api
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomType
import org.json.JSONObject

object RoomApi {
    private const val roomsUrl = "${Api.BASE_URL}/rooms"

    suspend fun getRoom(id: String): JSONObject? {
        return Api.get(getUrl(id))
    }

    suspend fun getRoomDevices(id: String): JSONObject? {
        return Api.get("${getUrl(id)}/devices")
    }

    suspend fun getAll(): List<Room> {
        val roomsJson = Api.get(getUrl())?.getJSONArray("result")
        val rooms = roomsJson?.let { array ->
            (0 until array.length()).map { index ->
                val room = array.getJSONObject(index)
                val devices = mutableListOf<String>()
                val devicesJson = Api.get("${getUrl(room.getString("id"))}/devices")?.getJSONArray("result")
                devicesJson?.let { array ->
                    (0 until array.length()).map { index ->
                        val device = array.getJSONObject(index)
                        devices.add(device.getString("id"))
                    }
                }
                Room(
                    id = room.getString("id"),
                    name = room.getString("name"),
                    roomType = RoomType.getRoomType(room.getJSONObject("meta").getString("type"))
                ).apply {
                    setDevices(devices)
                }
            }
        }?: emptyList()
        return rooms

    }

    private fun getUrl(slug: String? = null): String {
        return "$roomsUrl${slug?.let { "/$it" } ?: ""}"
    }
}
