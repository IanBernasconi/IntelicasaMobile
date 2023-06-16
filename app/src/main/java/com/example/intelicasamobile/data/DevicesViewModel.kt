package com.example.intelicasamobile.data


import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.Notifications
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.network.DeviceUpdateService
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.data.network.model.NetworkDeviceList
import com.example.intelicasamobile.data.persistent.PreferencesData
import com.example.intelicasamobile.data.persistent.NotificationType
import com.example.intelicasamobile.dataStore
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

    private var notificationPrefs: PreferencesData? = null

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

    fun startDeviceUpdateService(context: Context) {
        val intent = Intent(context, DeviceUpdateService::class.java)
        notificationPrefs = PreferencesData.getInstance(context.dataStore)
        context.startService(intent)
    }

    fun stopDeviceUpdateService(context: Context) {
        val intent = Intent(context, DeviceUpdateService::class.java)
        context.stopService(intent)
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

    fun fetchDevices(sendNotification: Boolean = true, context: Context) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _devicesUiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                notificationPrefs?.loadPreferences()
                apiService.getDevices()
            }.onSuccess { response ->
                val devices = getDevicesFromNetwork(response.body())
                if (sendNotification && devices != null) {
                    notifyDeviceDifferences(devices, context)
                }
                _devicesUiState.update {
                    it.copy(
                        devices = devices ?: emptyList(), isLoading = false
                    )
                }
            }.onFailure { e ->
                // Handle the error and notify the UI when appropriate.
                _devicesUiState.update {
                    it.copy(
                        message = e.message, isLoading = false
                    )
                }
            }
        }
    }

    private fun getDevicesFromNetwork(networkDeviceList: NetworkDeviceList?): List<Device>? {
        return networkDeviceList?.result?.let { array ->
            array.indices.map { index ->
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
                            temperature = networkDevice.networkState?.temperature?.roundToInt()
                                ?: 0,
                            mode = ACMode.getMode(networkDevice.networkState?.mode ?: "fan"),
                            fanSpeed = if (networkDevice.networkState?.fanSpeed == "auto") 0 else networkDevice.networkState?.fanSpeed?.toInt()
                                ?: 0,
                            verticalSwing = if (networkDevice.networkState?.verticalSwing == "auto") 0 else networkDevice.networkState?.verticalSwing?.toInt()
                                ?: 0,
                            horizontalSwing = if (networkDevice.networkState?.horizontalSwing == "auto") 0 else networkDevice.networkState?.horizontalSwing?.toInt()
                                ?: 0,
                        ),
                        deviceId = networkDevice.id,
                        deviceName = networkDevice.name,
                        deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                    )

                    DeviceType.OVEN -> OvenDevice(
                        initialState = OvenState(
                            isOn = networkDevice.networkState?.status == "on",
                            temperature = networkDevice.networkState?.temperature?.toInt() ?: 0,
                            heatMode = OvenHeatMode.getMode(
                                networkDevice.networkState?.heat ?: "top"
                            ),
                            grillMode = OvenGrillMode.getMode(
                                networkDevice.networkState?.grill ?: "off"
                            ),
                            convectionMode = OvenConvectionMode.getMode(
                                networkDevice.networkState?.convection ?: "off"
                            )
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
                            state = VacuumStateEnum.getMode(
                                networkDevice.networkState?.status ?: ""
                            ),
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
                            deviceMeta = Meta(networkDevice.meta?.favorite ?: false)
                        )
                    }
                }
            }
        }
    }

    private fun notifyDeviceDifferences(newDevices: List<Device>, context: Context) {
        newDevices.forEach { newDevice ->
            var message = ""
            var title = ""
            var showNotification = false
            val previousState = devicesUiState.value.devices.find { it.id == newDevice.id }

            if (previousState != null) {
                when (newDevice.deviceType) {
                    DeviceType.VACUUM_CLEANER -> {
                        val previousVacuumState = (previousState as VacuumDevice).state
                        val newVacuumState = (newDevice as VacuumDevice).state

                        if (notificationPrefs?.getLocalPreference(NotificationType.VACUUM_LOW_BATTERY.value) != false && previousVacuumState.value.batteryLevel > VacuumState.lowBatteryThreshold && newVacuumState.value.batteryLevel <= VacuumState.lowBatteryThreshold) {
                            message = context.getString(
                                R.string.vacuum_low_battery_info,
                                newDevice.name,
                                newVacuumState.value.batteryLevel
                            )
                            title = context.getString(R.string.vacuum_low_battery_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.VACUUM_EMPTY_BATTERY.value) != false && previousVacuumState.value.batteryLevel > 0 && newVacuumState.value.batteryLevel == 0) {
                            message = context.getString(
                                R.string.vacuum_empty_battery_info, newDevice.name
                            )
                            title = context.getString(R.string.vacuum_empty_battery_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.VACUUM_FULL_BATTERY.value) != false && previousVacuumState.value.batteryLevel < 100 && newVacuumState.value.batteryLevel == 100) {
                            message = context.getString(
                                R.string.vacuum_full_battery_info, newDevice.name
                            )
                            title = context.getString(R.string.vacuum_full_battery_title)
                            showNotification = true
                        }
                    }

                    DeviceType.DOOR -> {
                        val previousDoorState = (previousState as DoorDevice).state
                        val newDoorState = (newDevice as DoorDevice).state

                        if (notificationPrefs?.getLocalPreference(NotificationType.DOOR_UNLOCK.value) != false && previousDoorState.value.isLocked && !newDoorState.value.isLocked) {
                            message =
                                context.getString(R.string.door_unlocked_info, newDevice.name)
                            title = context.getString(R.string.door_unlocked_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.DOOR_LOCK.value) != false && !previousDoorState.value.isLocked && newDoorState.value.isLocked) {
                            message =
                                context.getString(R.string.door_locked_info, newDevice.name)
                            title = context.getString(R.string.door_locked_title)
                            showNotification = true

                        } else if (notificationPrefs?.getLocalPreference(NotificationType.DOOR_OPEN.value) != false && !previousDoorState.value.isOpen && newDoorState.value.isOpen) {
                            message =
                                context.getString(R.string.door_opened_info, newDevice.name)
                            title = context.getString(R.string.door_opened_title)
                            showNotification = true

                        } else if (notificationPrefs?.getLocalPreference(NotificationType.DOOR_CLOSE.value) != false && previousDoorState.value.isOpen && !newDoorState.value.isOpen) {
                            message =
                                context.getString(R.string.door_closed_info, newDevice.name)
                            title = context.getString(R.string.door_closed_title)
                            showNotification = true
                        }
                    }

                    DeviceType.OVEN -> {
                        val previousOvenState = (previousState as OvenDevice).state
                        val newOvenState = (newDevice as OvenDevice).state

                        if (notificationPrefs?.getLocalPreference(NotificationType.OVEN_ON.value) != false && !previousOvenState.value.isOn && newOvenState.value.isOn) {
                            message = context.getString(R.string.oven_on_info, newDevice.name)
                            title = context.getString(R.string.oven_on_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.OVEN_OFF.value) != false && previousOvenState.value.isOn && !newOvenState.value.isOn) {
                            message = context.getString(R.string.oven_off_info, newDevice.name)
                            title = context.getString(R.string.oven_off_title)
                            showNotification = true
                        }
                    }

                    DeviceType.AIR_CONDITIONER -> {
                        val previousAirConditionerState = (previousState as ACDevice).state
                        val newAirConditionerState = (newDevice as ACDevice).state

                        if (notificationPrefs?.getLocalPreference(NotificationType.AC_ON.value) != false && !previousAirConditionerState.value.isOn && newAirConditionerState.value.isOn) {
                            message = context.getString(R.string.ac_on_info, newDevice.name)
                            title = context.getString(R.string.ac_on_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.AC_OFF.value) != false && previousAirConditionerState.value.isOn && !newAirConditionerState.value.isOn) {
                            message = context.getString(R.string.ac_on_info, newDevice.name)
                            title = context.getString(R.string.ac_on_title)
                            showNotification = true
                        }
                    }

                    DeviceType.LAMP -> {
                        val previousLampState = (previousState as LightDevice).state
                        val newLampState = (newDevice as LightDevice).state

                        if (notificationPrefs?.getLocalPreference(NotificationType.LIGHT_ON.value) != false && !previousLampState.value.isOn && newLampState.value.isOn) {
                            message = context.getString(R.string.lamp_on_info, newDevice.name)
                            title = context.getString(R.string.lamp_on_title)
                            showNotification = true
                        } else if (notificationPrefs?.getLocalPreference(NotificationType.LIGHT_OFF.value) != false && previousLampState.value.isOn && !newLampState.value.isOn) {
                            message = context.getString(R.string.lamp_off_info, newDevice.name)
                            title = context.getString(R.string.lamp_off_title)
                            showNotification = true
                        }
                    }
                }
                if (showNotification) {
                    val notification = Notifications(
                        context = context, title = title, message = message
                    )
                    notification.showNotification(previousState.id.hashCode())
                }
            }
        }
    }
}