package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import com.example.intelicasamobile.data.network.data.DeviceApi
import com.example.intelicasamobile.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DevicesViewModel: ViewModel() {
    private val _devicesUiState = MutableStateFlow(DevicesUiState(emptyList()))
    val devicesUiState: StateFlow<DevicesUiState> = _devicesUiState.asStateFlow()

    private val devicesMutex = Mutex()

    suspend fun getDevices(refresh: Boolean = false): List<Device> {
        if (refresh || devicesUiState.value.devices.isEmpty()) {
            devicesMutex.withLock {
                val updatedDevices = mutableListOf<Device>()

                DeviceApi.getAll()?.forEach { device ->
                    val index = devicesUiState.value.devices.indexOfFirst { it.id == device.id }
                    if (index != -1) {
                        updatedDevices[index] = device
                    } else {
                        updatedDevices.add(device)
                    }
                }

                _devicesUiState.update { it.copy(devices = updatedDevices) }
            }
        }

        return devicesMutex.withLock { devicesUiState.value.devices }
    }
}