package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device

data class DevicesUiState(
    val devices: List<Device>,
    val isLoading: Boolean = false,
    val message: String? = null
)

val DevicesUiState.hasError : Boolean
    get() = message != null