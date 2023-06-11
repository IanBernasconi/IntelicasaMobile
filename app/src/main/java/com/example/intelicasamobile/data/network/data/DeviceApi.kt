package com.example.intelicasamobile.data.network.data
import Api
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceType
import com.example.intelicasamobile.model.DeviceTypeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object DeviceApi {
    private const val devicesUrl = "${Api.BASE_URL}/devices"
    private const val deviceTypesUrl = "${Api.BASE_URL}/devicetypes"

    var allCategories: List<DeviceTypeApi> = emptyList()
        private set

    fun getUrl(slug: String? = null): String {
        return "$devicesUrl${slug?.let { "/$it" } ?: ""}"
    }

    suspend fun getCategories(): List<DeviceTypeApi> {
        return withContext(Dispatchers.IO) {
            allCategories = Api.get(deviceTypesUrl)?.let { response ->
                response.getJSONArray("result").let { array ->
                    (0 until array.length()).map { index ->
                        val category = array.getJSONObject(index)
                        val deviceType =  DeviceType.values().find{ it.apiName == category.getString("name") }
                        DeviceTypeApi(id = category.getString("id"), type = deviceType ?: DeviceType.LAMP)
                    }
                }
            } ?: emptyList()

            return@withContext allCategories
        }
    }

    suspend fun add(device: Device): JSONObject? {
        if (allCategories.isEmpty()) {
            getCategories()
        }

        val deviceType = allCategories.find { category ->
            category.type.apiName == device.deviceType.apiName
        }

        deviceType?.let { type ->
            val body = JSONObject().apply {
                put("type", JSONObject().apply {
                    put("id", type.id)
                })
                put("name", device.name)
                put("meta", JSONObject().apply {
                    put("favorite", false)
                })
            }

            return Api.post(getUrl(), body)
        }

        return null
    }

    suspend fun delete(id: String): JSONObject? {
        return Api.delete(getUrl(id))
    }

    suspend fun getAll(): List<Device>? {
        val devicesJson = Api.get(getUrl())?.getJSONArray("result")
        return devicesJson?.let { array ->
            (0 until array.length()).map { index ->
                val device = array.getJSONObject(index)
                Device(
                    id = device.getString("id"),
                    sName = device.getString("name"),
               //     deviceType = DeviceType.valueOf(device.getJSONObject("type").getString("name"))
                    deviceType = DeviceType.values().find { it.apiName == device.getJSONObject("type").getString("name") } ?: DeviceType.LAMP
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

    suspend fun triggerEvent(event: Event): JSONObject? {
        return Api.put("$devicesUrl/${event.device.id}/${event.actionName}", event.params)
    }

    suspend fun toggleFavorite(device: Device): JSONObject? {
        val updatedDevice = JSONObject().apply {
            put("name", device.name)
            put("meta", device.meta.apply {
                put("favorite", !device.meta.favorite)
            })
        }

        return Api.put("$devicesUrl/${device.id}", updatedDevice)
    }
}

data class Event(val device: Device, val actionName: String, val params: JSONObject)
