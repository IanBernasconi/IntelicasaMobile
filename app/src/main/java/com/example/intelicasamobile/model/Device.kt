package com.example.intelicasamobile.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class Device(
    val id: String = "",
    val deviceType: DeviceType,
    val name: String = "",
    val meta: Meta = Meta(),
) : ViewModel() {

    private val isLoadingState = mutableStateOf(false)

    fun setLoadingState(isLoading: Boolean) {
        isLoadingState.value = isLoading
    }

    protected fun triggerNewAction(actionType: ActionTypes, params: List<String> = emptyList()) {
        viewModelScope.launch {
            isLoadingState.value = true
            val apiService = RetrofitClient.getApiService()
            apiService.triggerEvent(id = id, actionName = actionType.apiName, params = params)
            DevicesViewModel.getInstance().showSnackBar()
            isLoadingState.value = false
        }
    }

    fun isLoading(): Boolean = isLoadingState.value
}

data class ACDevice(

    val initialState: ACState = ACState(),
    val deviceId: String,
    val deviceName: String,
    val deviceMeta: Meta = Meta(),

) : Device(
    id = deviceId, name = deviceName, deviceType = DeviceType.AIR_CONDITIONER, meta = deviceMeta
) {

    private val _state: MutableStateFlow<ACState> = MutableStateFlow(initialState)
    val state: StateFlow<ACState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
        triggerNewAction(
            actionType = if (isOn) ActionTypes.TURN_ON else ActionTypes.TURN_OFF
        )

    }

    fun setTemperature(temperature: Int) {
        _state.update { it.copy(temperature = temperature) }
        triggerNewAction(
            actionType = ActionTypes.SET_TEMPERATURE, params = listOf(temperature.toString())
        )
    }

    fun setMode(mode: ACMode) {
        _state.update { it.copy(mode = mode) }
        triggerNewAction(
            actionType = ActionTypes.SET_MODE, params = listOf(mode.value)
        )
    }

    fun setFanSpeed(fanSpeed: Int) {
        _state.update { it.copy(fanSpeed = fanSpeed) }
        triggerNewAction(
            actionType = ActionTypes.SET_FAN_SPEED,
            params = listOf(if (fanSpeed == 0) "auto" else fanSpeed.toString())
        )
    }

    fun setVerticalSwing(verticalSwing: Int) {
        _state.update { it.copy(verticalSwing = verticalSwing) }
        triggerNewAction(
            actionType = ActionTypes.SET_VERTICAL_SWING,
            params = listOf(if (verticalSwing == 0) "auto" else verticalSwing.toString())
        )
    }

    fun setHorizontalSwing(horizontalSwing: Int) {
        _state.update { it.copy(horizontalSwing = horizontalSwing) }
        triggerNewAction(
            actionType = ActionTypes.SET_HORIZONTAL_SWING,
            params = listOf(if (horizontalSwing == -135) "auto" else horizontalSwing.toString())
        )
    }
}

data class LightDevice(

    val initialState: LightState = LightState(),
    val deviceId: String,
    val deviceName: String,
    val deviceMeta: Meta = Meta()

) : Device(
    id = deviceId, name = deviceName, deviceType = DeviceType.LAMP, meta = deviceMeta
) {

    private val _state: MutableStateFlow<LightState> = MutableStateFlow(initialState)
    val state: StateFlow<LightState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
        triggerNewAction(
            actionType = if (isOn) ActionTypes.TURN_ON else ActionTypes.TURN_OFF
        )
    }

    fun setBrightness(brightness: Int) {
        _state.update { it.copy(brightness = brightness) }
        triggerNewAction(
            actionType = ActionTypes.SET_BRIGHTNESS, params = listOf(brightness.toString())
        )
    }

    private fun convertToHexColor(color: Color): String {
        return String.format("%06X", (0xFFFFFF and color.toArgb()))
    }

    fun setColor(color: Color) {
        _state.update { it.copy(color = color) }
        triggerNewAction(
            actionType = ActionTypes.SET_COLOR, params = listOf(convertToHexColor(color))
        )
    }
}

data class OvenDevice(

    val initialState: OvenState = OvenState(),
    val deviceId: String,
    val deviceName: String,
    val deviceMeta: Meta = Meta()


) : Device(
    id = deviceId, name = deviceName, deviceType = DeviceType.OVEN, meta = deviceMeta
) {

    private val _state: MutableStateFlow<OvenState> = MutableStateFlow(initialState)
    val state: StateFlow<OvenState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
        triggerNewAction(
            actionType = if (isOn) ActionTypes.TURN_ON else ActionTypes.TURN_OFF
        )
    }

    fun setTemperature(temperature: Int) {
        _state.update { it.copy(temperature = temperature) }
        triggerNewAction(
            actionType = ActionTypes.SET_TEMPERATURE, params = listOf(temperature.toString())
        )
    }

    fun setHeatMode(heatMode: OvenHeatMode) {
        _state.update { it.copy(heatMode = heatMode) }
        triggerNewAction(
            actionType = ActionTypes.SET_HEAT_MODE, params = listOf(heatMode.value)
        )
    }

    fun setGrillMode(grillMode: OvenGrillMode) {
        _state.update { it.copy(grillMode = grillMode) }
        triggerNewAction(
            actionType = ActionTypes.SET_GRILL_MODE, params = listOf(grillMode.value)
        )
    }

    fun setConvectionMode(convectionMode: OvenConvectionMode) {
        _state.update { it.copy(convectionMode = convectionMode) }
        triggerNewAction(
            actionType = ActionTypes.SET_CONVECTION_MODE, params = listOf(convectionMode.value)
        )
    }
}

data class DoorDevice(

    val initialState: DoorState = DoorState(),
    val deviceId: String,
    val deviceName: String,
    val deviceMeta: Meta = Meta()

) : Device(
    id = deviceId, name = deviceName, deviceType = DeviceType.DOOR, meta = deviceMeta
) {

    private val _state: MutableStateFlow<DoorState> = MutableStateFlow(initialState)
    val state: StateFlow<DoorState> = _state.asStateFlow()

    fun setLocked(isLocked: Boolean) {
        _state.update { it.copy(isLocked = isLocked) }
        triggerNewAction(
            actionType = if (isLocked) ActionTypes.LOCK else ActionTypes.UNLOCK,
        )
    }

    fun setOpen(isOpen: Boolean) {
        _state.update { it.copy(isOpen = isOpen) }
        triggerNewAction(
            actionType = if (isOpen) ActionTypes.OPEN else ActionTypes.CLOSE,
        )
    }
}

data class VacuumDevice(

    val initialState: VacuumState = VacuumState(),
    val deviceId: String,
    val deviceName: String,
    val deviceMeta: Meta = Meta(),
    val dockLocationId: String? = null

) : Device(
    id = deviceId, name = deviceName, deviceType = DeviceType.VACUUM_CLEANER, meta = deviceMeta
) {

    private val _state: MutableStateFlow<VacuumState> = MutableStateFlow(initialState)
    val state: StateFlow<VacuumState> = _state.asStateFlow()

    fun setState(newState: VacuumStateEnum) {
        _state.update { it.copy(state = newState) }
        triggerNewAction(
            actionType = when (newState) {
                VacuumStateEnum.CLEANING -> ActionTypes.START_CLEANING
                VacuumStateEnum.PAUSED -> ActionTypes.PAUSE_CLEANING
                VacuumStateEnum.CHARGING -> ActionTypes.CHARGE
            }
        )
    }

    fun setMode(mode: VacuumCleanMode) {
        _state.update { it.copy(mode = mode) }
        triggerNewAction(
            actionType = ActionTypes.SET_MODE, params = listOf(mode.value)
        )
    }

    fun setLocation(location: String) {
        _state.update { it.copy(location = location) }
        triggerNewAction(
            actionType = ActionTypes.SET_LOCATION, params = listOf(location)
        )
    }
}
