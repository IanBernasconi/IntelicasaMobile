package com.example.intelicasamobile.ui

import com.example.intelicasamobile.data.Datasource
import com.example.intelicasamobile.data.MainUiState
import com.example.intelicasamobile.data.network.data.DeviceApi
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class MainViewModel {

    private val _mainUiState = MutableStateFlow(MainUiState(emptyList(), emptyList()))
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    private val devicesMutex = Mutex()

    suspend fun getDevices(refresh: Boolean = false): List<Device> {
        if (refresh || Datasource.dataDevices.isEmpty()) {
            devicesMutex.withLock {
                val updatedDevices = mutableListOf<Device>()

                DeviceApi.getAll()?.forEach { device ->
                    val index = mainUiState.value.devices.indexOfFirst { it.id == device.id }
                    if (index != -1) {
                        updatedDevices.add(device)
                    } else {
                        updatedDevices.add(device)
                    }
                }

                _mainUiState.update { it.copy(devices = updatedDevices) }
            }
        }

        return devicesMutex.withLock { mainUiState.value.devices }
    }


}