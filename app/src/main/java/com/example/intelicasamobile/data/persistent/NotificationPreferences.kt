package com.example.intelicasamobile.data.persistent

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NotificationPreferences private constructor(private val dataStore: DataStore<Preferences>) :
    ViewModel() {
    private val _preferences = MutableStateFlow(
        listOf(
            NotificationPreference(NotificationType.VACUUM_FULL_BATTERY),
            NotificationPreference(NotificationType.VACUUM_LOW_BATTERY),
            NotificationPreference(NotificationType.VACUUM_EMPTY_BATTERY),
            NotificationPreference(NotificationType.DOOR_UNLOCK),
            NotificationPreference(NotificationType.DOOR_OPEN),
            NotificationPreference(NotificationType.DOOR_CLOSE),
            NotificationPreference(NotificationType.DOOR_LOCK),
            NotificationPreference(NotificationType.LIGHT_ON),
            NotificationPreference(NotificationType.LIGHT_OFF),
            NotificationPreference(NotificationType.AC_ON),
            NotificationPreference(NotificationType.AC_OFF),
            NotificationPreference(NotificationType.OVEN_ON),
            NotificationPreference(NotificationType.OVEN_OFF),
        )
    )

    private val initialTruePreferences = listOf(
        NotificationType.DOOR_UNLOCK,
        NotificationType.VACUUM_FULL_BATTERY,
        NotificationType.VACUUM_LOW_BATTERY,
        NotificationType.VACUUM_EMPTY_BATTERY,
    )

    val preferences: StateFlow<List<NotificationPreference>> = _preferences.asStateFlow()

    suspend fun loadPreferences() {
        for (pref in preferences.value) {
            pref.loading.value = true
            pref.setValue(getPreference(pref.type.value))
            pref.loading.value = false
        }
    }

    private suspend fun getPreference(name: String): Boolean {
        val key = booleanPreferencesKey(name)
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: false
            }
            .first()
    }

    fun getLocalPreference(name: String): Boolean {
        val pref = preferences.value.find { it.type.value == name }
        return pref?.getValue() ?: false
    }

    fun setPreference(name: String, value: Boolean) {
        val pref = preferences.value.find { it.type.value == name }
        pref?.loading?.value = true
        val key = booleanPreferencesKey(name)
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
        pref?.setValue(value)
        pref?.loading?.value = false
    }

    private suspend fun setupFirstTimeAsync(): Boolean{
        val key = booleanPreferencesKey("isFirstTime")
        val isFirstTime = dataStore.data
            .map { preferences ->
                preferences[key] ?: true
            }
            .first()
        if (isFirstTime){
            for (pref in preferences.value) {
                if (pref.type in initialTruePreferences) {
                    pref.loading.value = true
                    setPreference(pref.type.value, true)
                    pref.loading.value = false
                }
            }
            dataStore.edit { preferences ->
                preferences[key] = false
            }
        }
        return isFirstTime
    }

    private fun setupFirstTime(){
        viewModelScope.launch {
            setupFirstTimeAsync()
        }
    }


    companion object {
        private var instance: NotificationPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): NotificationPreferences {
            if (instance == null) {
                instance = NotificationPreferences(dataStore)
                instance?.setupFirstTime()
            }
            return instance as NotificationPreferences
        }
    }


}
