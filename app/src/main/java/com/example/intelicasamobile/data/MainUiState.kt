package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainUiState {

    val devices: List<Device> = Datasource.devices
    val routines: List<Routine> = Datasource.routines

}