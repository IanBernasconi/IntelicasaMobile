package com.example.intelicasamobile.ui

import com.example.intelicasamobile.data.MainUiState
import com.example.intelicasamobile.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

}