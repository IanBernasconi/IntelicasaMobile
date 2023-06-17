package com.example.intelicasamobile

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.intelicasamobile.data.network.DeviceUpdateService

class IntelicasaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val intent = Intent(this, DeviceUpdateService::class.java)
        startService(intent)
    }

    companion object {
        const val CHANNEL_ID = "com.example.intelicasamobile.devices"
        var currentPath = "home"
    }


    private fun createNotificationChannel(){
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}