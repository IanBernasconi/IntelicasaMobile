package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.intelicasamobile.R

enum class DeviceTypes(
    @DrawableRes val imageResourceId: Int,
    @StringRes val nameResId: Int,
    val id: String = ""
) {
    LAMP(R.drawable.lightbulb, R.string.lamp),
    AIR_CONDITIONER(R.drawable.airconditioner, R.string.air_conditioner),
    OVEN(R.drawable.oven, R.string.oven),
    VACUUM_CLEANER(R.drawable.vacuumcleaner, R.string.vacuum_cleaner),
    DOOR(R.drawable.door, R.string.door),
}

class Category(
    val id: String = "",
    val nameResId: Int = -1,
    val name: String = "",
    val imageResourceId: Int = -1,
)