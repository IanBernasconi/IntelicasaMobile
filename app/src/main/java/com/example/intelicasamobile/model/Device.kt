package com.example.intelicasamobile.model

import androidx.annotation.StringRes

data class Device(
    val deviceType: DeviceTypes,
    @StringRes val name: Int,
    val meta: Meta = Meta()
)
