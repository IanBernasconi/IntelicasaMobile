package com.example.intelicasamobile.data.network.data

import Api
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceType
import com.example.intelicasamobile.model.DeviceTypeApi
import org.json.JSONObject

object DeviceApi {
    private const val devicesUrl = "${Api.BASE_URL}/devices"

    var allCategories: List<DeviceTypeApi> = emptyList()
        private set

    fun getUrl(slug: String? = null): String {
        return "$devicesUrl${slug?.let { "/$it" } ?: ""}"
    }

    suspend fun getAll(): List<Device>? {
        val devicesJson = Api.get(getUrl())?.getJSONArray("result")
        return devicesJson?.let { array ->
            (0 until array.length()).map { index ->
                val device = array.getJSONObject(index)
                Device(
                    id = device.getString("id"),
                    name = device.getString("name"),
                    deviceType = DeviceType.values().find { it.apiName == device.getJSONObject("type").getString("name") } ?: DeviceType.LAMP,
                    roomId = device.getJSONObject("room").getString("id")
                )
            }
        }
    }

    suspend fun getDevice(id: String): JSONObject? {
        return Api.get(getUrl(id))
    }

    suspend fun updateDevice(device: Device): JSONObject? {
        val updatedDevice = JSONObject().apply {
            put("name", device.name)
            put("meta", device.meta)
        }

        return Api.put(getUrl(device.id), updatedDevice)
    }

   // suspend fun triggerEvent(event: Event): JSONObject? {
   //     return Api.put("$devicesUrl/${event.device.id}/${event.actionName}", event.params)
   // }

}
