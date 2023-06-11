package com.example.intelicasamobile.model

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class Device(
    val id: String = "",
    val deviceType: DeviceType,
    val name: String = "",
    val meta: Meta = Meta(),
    val roomId: String = ""
) : ViewModel()

data class ACDevice(

    val initialState: ACState = ACState(),

    ) : Device(
    deviceType = DeviceType.AIR_CONDITIONER,
    meta = Meta(category = DeviceType.AIR_CONDITIONER)
) {

    private val _state: MutableStateFlow<ACState> = MutableStateFlow(ACState())
    val state: StateFlow<ACState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
    }

    fun setTemperature(temperature: Float) {
        _state.update { it.copy(temperature = temperature) }
    }

    fun setMode(mode: ACMode) {
        _state.update { it.copy(mode = mode) }
    }

    fun setFanSpeed(fanSpeed: Int) {
        _state.update { it.copy(fanSpeed = fanSpeed) }
    }

    fun setVerticalSwing(verticalSwing: Int) {
        _state.update { it.copy(verticalSwing = verticalSwing) }
    }

    fun setHorizontalSwing(horizontalSwing: Int) {
        _state.update { it.copy(horizontalSwing = horizontalSwing) }
    }
}

data class LightDevice(

    val initialState: LightState = LightState(),

    ) : Device(
    deviceType = DeviceType.LAMP,
    meta = Meta(category = DeviceType.LAMP)
) {

    private val _state: MutableStateFlow<LightState> = MutableStateFlow(initialState)
    val state: StateFlow<LightState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
    }

    fun setBrightness(brightness: Int) {
        _state.update { it.copy(brightness = brightness) }
    }

    fun setColor(color: Color) {
        _state.update { it.copy(color = color) }
    }
}

data class OvenDevice(

    val initialState: OvenState = OvenState(),

    ) : Device(
    deviceType = DeviceType.OVEN,
    meta = Meta(category = DeviceType.OVEN)
) {

    private val _state: MutableStateFlow<OvenState> = MutableStateFlow(initialState)
    val state: StateFlow<OvenState> = _state.asStateFlow()

    fun setIsOn(isOn: Boolean) {
        _state.update { it.copy(isOn = isOn) }
    }

    fun setTemperature(temperature: Int) {
        _state.update { it.copy(temperature = temperature) }
    }

    fun setHeatMode(heatMode: OvenHeatMode) {
        _state.update { it.copy(heatMode = heatMode) }
    }

    fun setGrillMode(grillMode: OvenGrillMode) {
        _state.update { it.copy(grillMode = grillMode) }
    }

    fun setConvectionMode(convectionMode: OvenConvectionMode) {
        _state.update { it.copy(convectionMode = convectionMode) }
    }
}

data class DoorDevice(

    val initialState: DoorState = DoorState(),

    ) : Device(
    deviceType = DeviceType.DOOR, meta = Meta(category = DeviceType.DOOR)
) {

    private val _state: MutableStateFlow<DoorState> = MutableStateFlow(initialState)
    val state: StateFlow<DoorState> = _state.asStateFlow()

    fun setLocked(isLocked: Boolean) {
        _state.update { it.copy(isLocked = isLocked) }
    }

    fun setOpen(isOpen: Boolean) {
        _state.update { it.copy(isOpen = isOpen) }
    }
}

data class VacuumDevice(

    val initialState: VacuumState = VacuumState(),

    ) : Device(
    deviceType = DeviceType.VACUUM_CLEANER,
    meta = Meta(category = DeviceType.VACUUM_CLEANER)
) {

    private val _state: MutableStateFlow<VacuumState> = MutableStateFlow(initialState)
    val state: StateFlow<VacuumState> = _state.asStateFlow()

    fun setBatteryPerc(batteryLevel: Int) {
        _state.update { it.copy(batteryLevel = batteryLevel) }
    }

    fun setState(newState: VacuumStateEnum) {
        _state.update { it.copy(state = newState) }
    }

    fun setMode(mode: VacuumCleanMode) {
        _state.update { it.copy(mode = mode) }
    }

    fun setLocation(location: String) {
        _state.update { it.copy(location = location) }
    }
}
