package com.example.intelicasamobile.data

import com.example.intelicasamobile.model.Routine

data class RoutinesUiState (
    val routines: List<Routine>,
    val isLoading: Boolean = false,
    val showSnackBar: Boolean = false,
    val message: String? = null
    )

val RoutinesUiState.hasError : Boolean
    get() = message != null