package com.example.intelicasamobile.data


import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.ACMode
import com.example.intelicasamobile.model.ACState
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceType
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class DevicesViewModel private constructor() : ViewModel() {
    private val _devicesUiState = MutableStateFlow(DevicesUiState(emptyList()))
    val devicesUiState: StateFlow<DevicesUiState> = _devicesUiState.asStateFlow()

    private var fetchJob: Job? = null

    companion object {
        private var instance: DevicesViewModel? = null

        fun getInstance(): DevicesViewModel {
            if (instance == null) {
                instance = DevicesViewModel()
            }
            return instance as DevicesViewModel
        }
    }

    fun dismissMessage() {
        _devicesUiState.update { it.copy(message = null) }
    }

    fun showSnackBar() {
        _devicesUiState.update { it.copy(showSnackBar = true) }
    }

    fun dismissSnackBar() {
        _devicesUiState.update { it.copy(showSnackBar = false) }
    }

    fun fetchDevices(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _devicesUiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getDevices()
            }.onSuccess { response ->
                val devices = getDevicesFromNetwork(response.body())
                println("Devices: $devices")
                _devicesUiState.update { it.copy(
                    devices = devices?: emptyList(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                // Handle the error and notify the UI when appropriate.
                _devicesUiState.update { it.copy(
                    message = e.message,
                    isLoading = false
                ) }
            }
        }
    }

    private fun getDevicesFromNetwork(networkDeviceList: NetworkDeviceList?): List<Device>? {
        return networkDeviceList?.result?.let{array ->
            (0 until array.size).map{ index ->
                val networkDevice = array[index]
                when (networkDevice.networkType?.name?.let { DeviceType.getDeviceType(it) }) {
                    DeviceType.LAMP -> LightDevice(
                        initialState = LightState(
                            isOn = networkDevice.networkState?.status == "on",
                            brightness = networkDevice.networkState?.brightness?.toInt() ?: 0,
                            color = Color(android.graphics.Color.parseColor("#" + networkDevice.networkState?.color))
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                    )

                    DeviceType.AIR_CONDITIONER -> ACDevice(
                        initialState = ACState(
                            isOn = networkDevice.networkState?.status == "on",
                            temperature = networkDevice.networkState?.temperature?.roundToInt() ?: 0,
                            mode = ACMode.getMode(networkDevice.networkState?.mode ?: "fan"),
                            fanSpeed = if (networkDevice.networkState?.fanSpeed == "auto") 0 else networkDevice.networkState?.fanSpeed?.toInt() ?: 0,
                            verticalSwing = if (networkDevice.networkState?.verticalSwing == "auto") 0 else networkDevice.networkState?.verticalSwing?.toInt() ?: 0,
                            horizontalSwing = if (networkDevice.networkState?.horizontalSwing == "auto") 0 else networkDevice.networkState?.horizontalSwing?.toInt() ?: 0,
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                    )

                    DeviceType.OVEN -> OvenDevice(
                        initialState = OvenState(
                            isOn = networkDevice.networkState?.status == "on",
                            temperature = networkDevice.networkState?.temperature?.toInt() ?: 0,
                            heatMode = OvenHeatMode.getMode(networkDevice.networkState?.heat ?: "top"),
                            grillMode = OvenGrillMode.getMode(networkDevice.networkState?.grill ?: "off"),
                            convectionMode = OvenConvectionMode.getMode(networkDevice.networkState?.convection ?: "off")
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                    )

                    DeviceType.DOOR -> DoorDevice(
                        initialState = DoorState(
                            isLocked = networkDevice.networkState?.locked == "locked",
                            isOpen = networkDevice.networkState?.status == "opened"
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                    )

                    DeviceType.VACUUM_CLEANER -> VacuumDevice(
                        initialState = VacuumState(
                            batteryLevel = networkDevice.networkState?.batteryLevel ?: 0,
                            state = VacuumStateEnum.getMode(networkDevice.networkState?.status ?: ""),
                            mode = VacuumCleanMode.getMode(networkDevice.networkState?.mode ?: ""),
                            location = networkDevice.networkState?.location?.id ?: "",
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false),
                        dockLocationId = networkDevice.room?.id
                    )

                    else -> {
                        LightDevice(
                            initialState = LightState(
                                isOn = networkDevice.networkState?.status == "on",
                                brightness = networkDevice.networkState?.brightness?.toInt() ?: 0,
                                color = Color(android.graphics.Color.parseColor("#" + networkDevice.networkState?.color))
                            ),
                            deviceId = networkDevice.id,
                            deviceName = networkDevice.name,
                            deviceMeta = Meta(networkDevice.meta?.favorite ?: false))
                    }
                }
            }
        }
    }
}