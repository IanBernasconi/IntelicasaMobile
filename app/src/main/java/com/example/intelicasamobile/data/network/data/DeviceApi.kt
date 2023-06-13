package com.example.intelicasamobile.data.network.data

import com.example.intelicasamobile.model.Action
import Api
import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACMode
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceType
import com.example.intelicasamobile.model.DeviceTypeApi
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.DoorState
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.LightState
import com.example.intelicasamobile.model.Meta
import com.example.intelicasamobile.model.OvenConvectionMode
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.OvenGrillMode
import com.example.intelicasamobile.model.OvenHeatMode
import com.example.intelicasamobile.model.OvenState
import com.example.intelicasamobile.model.VacuumCleanMode
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.model.VacuumState
import com.example.intelicasamobile.model.VacuumStateEnum
import org.json.JSONArray
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
                val state = device.getJSONObject("state")
                when (DeviceType.getDeviceType(device.getJSONObject("type").getString("name"))) {
                    DeviceType.LAMP -> LightDevice(
                        initialState = LightState(
                            isOn = state.getString("status") == "on",
                            brightness = state.getInt("brightness"),
                            color = Color(parseColor("#" + state.getString("color")))
                        ),
                        deviceId = device.getString("id"),
                        deviceName = device.getString("name"),
                        deviceMeta = Meta(device.getJSONObject("meta").optBoolean("favorite"))
                    )

                    DeviceType.AIR_CONDITIONER -> ACDevice(
                        initialState = ACState(
                            isOn = state.getString("status") == "on",
                            temperature = state.getInt("temperature").toFloat(),
                            mode = ACMode.getMode(state.getString("mode")),
                            fanSpeed = if (state.getString("fanSpeed") == "auto") 0 else state.getInt(
                                "fanSpeed"
                            ),
                            verticalSwing = if (state.getString("verticalSwing") == "auto") 0 else state.getInt(
                                "verticalSwing"
                            ),
                            horizontalSwing = if (state.getString("horizontalSwing") == "auto") -135 else state.getInt(
                                "horizontalSwing"
                            )
                        ),
                        deviceId = device.getString("id"),
                        deviceName = device.getString("name"),
                        deviceMeta = Meta(device.getJSONObject("meta").optBoolean("favorite"))
                    )

                    DeviceType.OVEN -> OvenDevice(
                        initialState = OvenState(
                            isOn = state.getString("status") == "on",
                            temperature = state.getInt("temperature"),
                            heatMode = OvenHeatMode.getMode(state.getString("heat")),
                            grillMode = OvenGrillMode.getMode(state.getString("grill")),
                            convectionMode = OvenConvectionMode.getMode(state.getString("convection"))
                        ),
                        deviceId = device.getString("id"),
                        deviceName = device.getString("name"),
                        deviceMeta = Meta(device.getJSONObject("meta").optBoolean("favorite"))
                    )

                    DeviceType.DOOR -> DoorDevice(
                        initialState = DoorState(
                            isLocked = state.getString("status") == "locked",
                            isOpen = state.getString("status") == "opened"
                        ),
                        deviceId = device.getString("id"),
                        deviceName = device.getString("name"),
                        deviceMeta = Meta(device.getJSONObject("meta").optBoolean("favorite"))

                    )

                    DeviceType.VACUUM_CLEANER -> VacuumDevice(
                        initialState = VacuumState(
                            batteryLevel = state.getInt("batteryLevel"),
                            state = VacuumStateEnum.getMode(state.getString("status")),
                            mode = VacuumCleanMode.getMode(state.getString("mode"))
                            //TODO location
                        ),
                        deviceId = device.getString("id"),
                        deviceName = device.getString("name"),
                        deviceMeta = Meta(device.getJSONObject("meta").optBoolean("favorite")),
                        locationId = state.optJSONObject("location")?.optString("id")
                    )
                }
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

        return Api.put(getUrl(device.id), updatedDevice.toString())
    }

    suspend fun triggerEvent(event: Action): JSONObject? {
        println(JSONArray(event.params.toString()))
        return Api.put(
            "$devicesUrl/${event.deviceId}/${event.action.apiName}",
            JSONArray(event.params.toString()).toString()
        )
    }

}
