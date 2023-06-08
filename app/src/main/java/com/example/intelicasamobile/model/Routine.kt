package com.example.intelicasamobile.model

import android.graphics.drawable.Icon
import androidx.annotation.StringRes

data class Routine(
    @StringRes val name: Int,
    val devices: List<Device>
)
