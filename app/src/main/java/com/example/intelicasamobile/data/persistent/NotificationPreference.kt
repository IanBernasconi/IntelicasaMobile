package com.example.intelicasamobile.data.persistent

import androidx.compose.runtime.mutableStateOf

class NotificationPreference(
    val type: NotificationType
) {
    val value = mutableStateOf(false)
    val loading = mutableStateOf(false)

    fun getValue(): Boolean {
        return value.value
    }

    fun setValue(value: Boolean) {
        this.value.value = value
    }
}
