package com.example.intelicasamobile.model

import androidx.compose.ui.graphics.Color

open class State {
    val isOn: Boolean = false
}

class LightState(
    val brightness: Int = 0,
    val color: Color = Color.White
) : State()


class ACState(
    val temperature: Float = 0f,
    val mode: ACMode = ACMode.VENTILACION,
    val fanSpeed: Int = 0,
    val verticalSwing: Int = 0,
    val horizontalSwing: Int = -135
) : State()