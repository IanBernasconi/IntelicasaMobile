package com.example.intelicasamobile.data.persistent


class NotificationPreference(
    val type: NotificationType
) : Preference()

class ThemePreference(
    val type: ThemePreferenceType
) : Preference()