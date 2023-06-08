package com.example.intelicasamobile.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R

open class State()

class LightState(
    var brightness: Int = 0, var color: Color = Color.White, var isOn: Boolean = false
) : State()


class ACState(
    var isOn: Boolean = false,
    var temperature: Float = 0f,
    var mode: ACMode = ACMode.FAN,
    var fanSpeed: Int = 0,
    var verticalSwing: Int = 0,
    var horizontalSwing: Int = -135
) : State()

class OvenState(
    var isOn: Boolean = false,
    var temperature: Int = 90,
    var heatMode: OvenHeatMode = OvenHeatMode.CONVENTIONAL,
    var grillMode: OvenGrillMode = OvenGrillMode.CONVENTIONAL,
    var convectionMode: OvenConvectionMode = OvenConvectionMode.CONVENTIONAL
) : State()

class DoorState(
    var isLocked: Boolean = false, var isOpen: Boolean = false
) : State()

class VacuumState(
    var batteryPerc: Int = 0,
    var state: VacuumStateEnum = VacuumStateEnum.CHARGING,
    var mode: VacuumCleanMode = VacuumCleanMode.VACUUM,
    var location: String = "" //TODO update with location
) : State()

enum class VacuumStateEnum(
    @StringRes val nameResId: Int
) {
    CLEANING(R.string.VS_cleaning), CHARGING(R.string.VS_charging), PAUSED(R.string.VS_paused)
}