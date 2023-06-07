package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import com.example.intelicasamobile.R

enum class ACMode(
    @DrawableRes val imageResourceId: Int,
    value : String
) {
    VENTILACION(R.drawable.acventilationmode, "fan"),
    FRIO(R.drawable.accoldmode, "cool"),
    CALOR(R.drawable.acheatmode, "heat")
}