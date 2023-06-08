package com.example.intelicasamobile.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R

open class Device(
    val deviceType: DeviceTypes,
    @StringRes val name: Int,
    val meta: Meta = Meta(),
)

data class ACDevice(val state: ACState = ACState()) : Device(
    DeviceTypes.AIR_CONDITIONER,
    R.string.air_conditioner,
    meta = Meta(category = DeviceTypes.AIR_CONDITIONER)
) {
    fun setIsOn(isOn: Boolean) {
        state.isOn = isOn
    }

    fun setTemperature(temperature: Float) {
        state.temperature = temperature
    }

    fun setMode(mode: ACMode) {
        state.mode = mode
    }

    fun setFanSpeed(fanSpeed: Int) {
        state.fanSpeed = fanSpeed
    }

    fun setVerticalSwing(verticalSwing: Int) {
        state.verticalSwing = verticalSwing
    }

    fun setHorizontalSwing(horizontalSwing: Int) {
        state.horizontalSwing = horizontalSwing
    }
}

data class LightDevice(val state: LightState = LightState()) : Device(
    deviceType = DeviceTypes.LAMP,
    name = R.string.lamp,
    meta = Meta(category = DeviceTypes.LAMP)
) {
    fun setBrightness(brightness: Int) {
        state.brightness = brightness
    }

    fun setColor(color: Color) {
        state.color = color
    }

    fun setIsOn(isOn: Boolean) {
        state.isOn = isOn
    }
}

data class OvenDevice(val state: OvenState = OvenState()) : Device(
    deviceType = DeviceTypes.OVEN,
    name = R.string.oven,
    meta = Meta(category = DeviceTypes.OVEN)
) {
    fun setIsOn(isOn: Boolean) {
        state.isOn = isOn
    }

    fun setTemperature(temperature: Int) {
        state.temperature = temperature
    }

    fun setHeatMode(heatMode: OvenHeatMode) {
        state.heatMode = heatMode
    }

    fun setGrillMode(grillMode: OvenGrillMode) {
        state.grillMode = grillMode
    }

    fun setConvectionMode(convectionMode: OvenConvectionMode) {
        state.convectionMode = convectionMode
    }
}

data class DoorDevice(val state: DoorState = DoorState()) : Device(
    deviceType = DeviceTypes.DOOR,
    name = R.string.door,
    meta = Meta(category = DeviceTypes.DOOR)
) {
    fun setLocked(isLocked: Boolean) {
        state.isLocked = isLocked
    }

    fun setOpen(isOpen: Boolean) {
        state.isOpen = isOpen
    }
}

data class VacuumDevice(val state: VacuumState = VacuumState()) : Device(
    deviceType = DeviceTypes.VACUUM_CLEANER,
    name = R.string.vacuum_cleaner,
    meta = Meta(category = DeviceTypes.VACUUM_CLEANER)
) {
    fun setBatteryPerc(batteryPerc: Int) {
        state.batteryPerc = batteryPerc
    }

    fun setState(newState: VacuumStateEnum) {
        state.state = newState
    }

    fun setMode(mode: VacuumCleanMode) {
        state.mode = mode
    }

    fun setLocation(location: String) {
        state.location = location
    }
}
