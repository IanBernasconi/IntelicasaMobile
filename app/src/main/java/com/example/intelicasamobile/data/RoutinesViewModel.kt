package com.example.intelicasamobile.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.data.network.data.RoutineApi
import com.example.intelicasamobile.data.network.model.NetworkRoutineList
import com.example.intelicasamobile.model.Action
import com.example.intelicasamobile.model.ActionTypes
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.Meta
import com.example.intelicasamobile.model.Routine
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RoutinesViewModel: ViewModel() {
    private val _routinesUiState = MutableStateFlow(RoutinesUiState(emptyList()))
    val routinesUiState: StateFlow<RoutinesUiState> = _routinesUiState.asStateFlow()

    private var fetchJob: Job? = null
    fun dismissMessage() {
        _routinesUiState.update { it.copy(message = null) }
    }

    fun fetchRoutines(){
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _routinesUiState.update { it.copy(isLoading = true) }
            runCatching {
                val apiService = RetrofitClient.getApiService()
                apiService.getRoutines()
            }.onSuccess { response ->
                println("Response: $response")
                val routines = getRoutinesFromNetwork(response.body())
                println("Routines: $routines")
                _routinesUiState.update { it.copy(
                    routines = routines?: emptyList(),
                    isLoading = false
                ) }
            }.onFailure { e ->
                _routinesUiState.update { it.copy(
                    isLoading = false,
                    message = e.message
                ) }
            }
        }
    }

    private fun getRoutinesFromNetwork(networkRoutineList: NetworkRoutineList?): List<Routine>? {
        return networkRoutineList?.result?.let { array ->
            (0 until array.size).map{ index ->
                val networkRoutine = array[index]
                Routine(
                    id = networkRoutine.id,
                    name = networkRoutine.name,
                    actions = networkRoutine.actions.let{
                        (0 until it.size).map{ index ->
                            val networkAction = it[index]
                            Action(
                                action = ActionTypes.getActionType(networkAction.actionName) ?: ActionTypes.TURN_ON,
                                deviceId = networkAction.device.id ?: "",
                                params = listOf(
                                    if(networkAction.params.isNotEmpty()) networkAction.params[0] else ""
                                )
                            )
                        }
                    },
                    meta = Meta(networkRoutine.meta?.favorite ?: false)
                )
            }

        }
    }


}