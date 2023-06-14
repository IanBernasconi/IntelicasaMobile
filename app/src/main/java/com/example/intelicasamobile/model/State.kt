package com.example.intelicasamobile.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.intelicasamobile.R

open class State

data class LightState(
    val brightness: Int = 0,
    val color: Color = Color.White,
    val isOn: Boolean = false
) : State()


data class ACState(
    val isOn: Boolean = false,
    val temperature: Int = 0,
    val mode: ACMode = ACMode.FAN,
    val fanSpeed: Int = 0,
    val verticalSwing: Int = 0,
    val horizontalSwing: Int = -135
) : State()

data class OvenState(
    val isOn: Boolean = false,
    val temperature: Int = 90,
    val heatMode: OvenHeatMode = OvenHeatMode.CONVENTIONAL,
    val grillMode: OvenGrillMode = OvenGrillMode.CONVENTIONAL,
    val convectionMode: OvenConvectionMode = OvenConvectionMode.CONVENTIONAL
) : State()

data class DoorState(
    val isLocked: Boolean = false,
    val isOpen: Boolean = false
) : State()

data class VacuumState(
    val batteryLevel: Int = 0,
    val state: VacuumStateEnum = VacuumStateEnum.CHARGING,
    val mode: VacuumCleanMode = VacuumCleanMode.VACUUM,
    val location: String = ""
) : State()

enum class VacuumStateEnum(
    @StringRes val nameResId: Int,
    val value:String
) {
    CLEANING(R.string.VS_cleaning, "active"),
    CHARGING(R.string.VS_charging, "docked"),
    PAUSED(R.string.VS_paused,"inactive");

    companion object {
        fun getMode(value: String): VacuumStateEnum {
            return when (value) {
                "active" -> CLEANING
                "docked" -> CHARGING
                "inactive" -> PAUSED
                else -> PAUSED
            }
        }
    }
}