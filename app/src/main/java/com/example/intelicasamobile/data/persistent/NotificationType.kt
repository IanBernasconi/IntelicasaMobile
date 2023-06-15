package com.example.intelicasamobile.data.persistent

import androidx.annotation.StringRes
import com.example.intelicasamobile.R

enum class NotificationType(
    val value: String,
    @StringRes val nameResId: Int
) {
    DOOR_OPEN("onDoorOpen", R.string.pref_on_door_open),
    DOOR_CLOSE("onDoorClose", R.string.pref_on_door_closed),
    DOOR_LOCK("onDoorLock", R.string.pref_on_door_locked),
    DOOR_UNLOCK("onDoorUnlock", R.string.pref_on_door_unlocked),
    VACUUM_FULL_BATTERY("onVacuumFullBattery", R.string.pref_on_vacuum_full_battery),
    VACUUM_LOW_BATTERY("onVacuumLowBattery", R.string.pref_on_vacuum_low_battery),
    VACUUM_EMPTY_BATTERY("onVacuumEmptyBattery", R.string.pref_on_vacuum_empty_battery),
    LIGHT_ON("onLightOn", R.string.pref_on_light_on),
    LIGHT_OFF("onLightOff", R.string.pref_on_light_off),
    AC_ON("onAcOn", R.string.pref_on_ac_on),
    AC_OFF("onAcOff", R.string.pref_on_ac_off),
    OVEN_ON("onOvenOn", R.string.pref_on_oven_on),
    OVEN_OFF("onOvenOff", R.string.pref_on_oven_off),
}