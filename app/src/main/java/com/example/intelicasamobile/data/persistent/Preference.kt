package com.example.intelicasamobile.data.persistent

import androidx.compose.runtime.mutableStateOf

open class Preference {
    val value = mutableStateOf(false)
    val loading = mutableStateOf(false)

    fun getValue(): Boolean {
        return value.value
    }

    fun setValue(value: Boolean) {
        this.value.value = value
    }
}