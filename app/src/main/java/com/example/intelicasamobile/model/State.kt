package com.example.intelicasamobile.model

import androidx.compose.ui.graphics.Color

open class State (
    val isOn: Boolean = false
)

class LightState(
    val brightness: Int = 0,
    val color: Color = Color.White,
    isOn : Boolean = false
) : State(isOn)


class ACState(
    isOn: Boolean = false,
    val temperature: Float = 0f,
    val mode: ACMode = ACMode.FAN,
    val fanSpeed: Int = 0,
    val verticalSwing: Int = 0,
    val horizontalSwing: Int = -135
) : State(isOn)

class OvenState(
    isOn: Boolean = false,
    val temperature: Float = 90f,
    val heatMode: OvenHeatMode = OvenHeatMode.CONVENTIONAL,
    val grillMode: OvenGrillMode = OvenGrillMode.CONVENTIONAL,
    val convectionMode: OvenConvectionMode = OvenConvectionMode.CONVENTIONAL
) : State(isOn)