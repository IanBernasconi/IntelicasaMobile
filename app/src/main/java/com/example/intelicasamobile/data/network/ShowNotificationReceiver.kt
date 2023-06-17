package com.example.intelicasamobile.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.intelicasamobile.Notifications.Companion.showNotification
import com.example.intelicasamobile.data.MyIntent

class ShowNotificationReceiver : BroadcastReceiver() {

    companion object{
        const val TAG = "ShowNotificationReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        val deviceId = intent.getStringExtra(MyIntent.DEVICE_ID)
        val title = intent.getStringExtra(MyIntent.NOTIFICATION_TITLE) ?: ""
        val message = intent.getStringExtra(MyIntent.NOTIFICATION_MESSAGE) ?: ""
        showNotification(context, title, message, deviceId.hashCode())
    }
}