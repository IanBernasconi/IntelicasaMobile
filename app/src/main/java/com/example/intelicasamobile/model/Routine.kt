package com.example.intelicasamobile.model

data class Routine(
    val id: String,
    val name: String,
    val actions: List<Action>,
)
