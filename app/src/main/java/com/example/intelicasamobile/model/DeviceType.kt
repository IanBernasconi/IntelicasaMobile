package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.intelicasamobile.R

enum class DeviceType(
    @DrawableRes val imageResourceId: Int,
    @StringRes val nameResId: Int,
    val apiName: String
) {
    LAMP(R.drawable.lightbulb, R.string.lamp, "lamp"),
    AIR_CONDITIONER(R.drawable.airconditioner, R.string.air_conditioner, "ac"),
    OVEN(R.drawable.oven, R.string.oven, "oven"),
    VACUUM_CLEANER(R.drawable.vacuumcleaner, R.string.vacuum_cleaner, "vacuum"),
    DOOR(R.drawable.door, R.string.door, "door"),
}

class DeviceTypeApi(
    val id: String,
    val type: DeviceType
)