package com.example.intelicasamobile.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R

open class State()

class LightState(
    var brightness: Int = 0, var color: Color = Color.White, var isOn: Boolean = false
) : State() {
    fun setBrightness(brightness: Int) {
        this.brightness = brightness
    }

    fun setColor(color: Color) {
        this.color = color
    }

    fun setIsOn(isOn: Boolean) {
        this.isOn = isOn
    }
}


class ACState(
    var isOn: Boolean = false,
    var temperature: Float = 0f,
    var mode: ACMode = ACMode.FAN,
    var fanSpeed: Int = 0,
    var verticalSwing: Int = 0,
    var horizontalSwing: Int = -135
) : State() {
    fun setIsOn(isOn: Boolean) {
        this.isOn = isOn
    }

    fun setTemperature(temperature: Float) {
        this.temperature = temperature
    }

    fun setMode(mode: ACMode) {
        this.mode = mode
    }

    fun setFanSpeed(fanSpeed: Int) {
        this.fanSpeed = fanSpeed
    }

    fun setVerticalSwing(verticalSwing: Int) {
        this.verticalSwing = verticalSwing
    }

    fun setHorizontalSwing(horizontalSwing: Int) {
        this.horizontalSwing = horizontalSwing
    }
}

class OvenState(
    var isOn: Boolean = false,
    var temperature: Int = 90,
    var heatMode: OvenHeatMode = OvenHeatMode.CONVENTIONAL,
    var grillMode: OvenGrillMode = OvenGrillMode.CONVENTIONAL,
    var convectionMode: OvenConvectionMode = OvenConvectionMode.CONVENTIONAL
) : State() {
    fun setIsOn(isOn: Boolean) {
        this.isOn = isOn
    }

    fun setTemperature(temperature: Int) {
        this.temperature = temperature
    }

    fun setHeatMode(heatMode: OvenHeatMode) {
        this.heatMode = heatMode
    }

    fun setGrillMode(grillMode: OvenGrillMode) {
        this.grillMode = grillMode
    }

    fun setConvectionMode(convectionMode: OvenConvectionMode) {
        this.convectionMode = convectionMode
    }
}

class DoorState(
    var isLocked: Boolean = false, var isOpen: Boolean = false
) : State() {
    fun setLocked(isLocked: Boolean) {
        this.isLocked = isLocked
    }

    fun setOpen(isOpen: Boolean) {
        this.isOpen = isOpen
    }
}

class VacuumState(
    var batteryPerc: Int = 0,
    var state: VacuumStateEnum = VacuumStateEnum.CHARGING,
    var mode: VacuumCleanMode = VacuumCleanMode.VACUUM,
    var location: String = "" //TODO update with location
) : State() {
    fun setBatteryPerc(batteryPerc: Int) {
        this.batteryPerc = batteryPerc
    }

    fun setState(state: VacuumStateEnum) {
        this.state = state
    }

    fun setMode(mode: VacuumCleanMode) {
        this.mode = mode
    }

    fun setLocation(location: String) {
        this.location = location
    }
}

enum class VacuumStateEnum(
    @StringRes val nameResId: Int
) {
    CLEANING(R.string.VS_cleaning), CHARGING(R.string.VS_charging), PAUSED(R.string.VS_paused)
}