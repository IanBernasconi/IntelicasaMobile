package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import com.example.intelicasamobile.data.network.data.DeviceApi
import com.example.intelicasamobile.data.network.data.RoutineApi
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesViewModel: ViewModel() {
    private val _routinesUiState = MutableStateFlow(RoutinesUiState(emptyList()))
    val routinesUiState: StateFlow<RoutinesUiState> = _routinesUiState.asStateFlow()

    private val routinesMutex = Mutex()

    suspend fun getRoutines(refresh: Boolean = false): List<Routine> {
        if (refresh || routinesUiState.value.routines.isEmpty()) {
            routinesMutex.withLock {
                val updatedRoutines = mutableListOf<Routine>()

                RoutineApi.getAll()?.forEach { routine ->
                    val index = routinesUiState.value.routines.indexOfFirst { it.name == routine.name } //TODO chequear esta comparación
                    if (index != -1) {
                        updatedRoutines[index] = routine
                    } else {
                        updatedRoutines.add(routine)
                    }
                }

                _routinesUiState.update { it.copy(routines = updatedRoutines) }
            }
        }

        return routinesMutex.withLock { routinesUiState.value.routines }
    }
}