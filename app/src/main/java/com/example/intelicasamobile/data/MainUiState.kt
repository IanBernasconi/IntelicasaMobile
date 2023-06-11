package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Routine


data class MainUiState(
    val devices: List<Device>,
    val routines: List<Routine>
)