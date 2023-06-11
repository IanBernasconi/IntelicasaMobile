package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Routine


class MainUiState {

    val devices: MutableList<Device> = Datasource.dataDevices
    val routines: List<Routine> = Datasource.routines

}