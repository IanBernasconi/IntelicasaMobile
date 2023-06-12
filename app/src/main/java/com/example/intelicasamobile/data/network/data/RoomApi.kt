package com.example.intelicasamobile.data.network.data

import Api
import com.example.intelicasamobile.model.Room
import com.example.intelicasamobile.model.RoomType
import org.json.JSONObject

object RoomApi {
    private const val roomsUrl = "${Api.BASE_URL}/rooms"


    suspend fun getRoom(id: String): JSONObject? {
        return Api.get(getUrl(id))
    }

    suspend fun getAll(): List<Room> {
        val roomsJson = Api.get(getUrl())?.getJSONArray("result")
        val rooms = roomsJson?.let { array ->
            (0 until array.length()).map { index ->
                val room = array.getJSONObject(index)
                Room(
                    id = room.getString("id"),
                    name = room.getString("name"),
                    roomType = RoomType.getRoomType(  room.getJSONObject("meta").getString("type"))
                )
            }
        } ?: emptyList()
        return rooms
    }

    private fun getUrl(slug: String? = null): String {
        return "$roomsUrl${slug?.let { "/$it" } ?: ""}"
    }
}
