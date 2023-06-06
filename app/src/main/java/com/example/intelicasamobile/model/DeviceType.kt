package com.example.intelicasamobile.model

import androidx.annotation.DrawableRes
import com.example.intelicasamobile.R

enum class DeviceTypes(
    @DrawableRes val imageResourceId: Int
) {
    LAMP(R.drawable.lightbulb),
    AIR_CONDITIONER(R.drawable.airconditioner),
    OVEN(R.drawable.oven),
    VACUUM_CLEANER(R.drawable.vacuumcleaner),
    DOOR(R.drawable.door),
}