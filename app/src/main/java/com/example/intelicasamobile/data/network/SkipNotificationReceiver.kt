package com.example.intelicasamobile.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.intelicasamobile.data.MyIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SkipNotificationReceiver() : BroadcastReceiver(), CoroutineScope {

    companion object {
        const val TAG = "SkipNotificationReceiver"
    }

    private val devicesList = mutableSetOf<String>()

    private var removeJob: Job? = null

    override val coroutineContext: CoroutineContext get() = Dispatchers.IO

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        val deviceId = intent.getStringExtra(MyIntent.DEVICE_ID)
        if (intent.action.equals(MyIntent.SHOW_NOTIFICATION) &&
            devicesList.contains(deviceId)
        ) {
            Log.d(TAG, "onReceive: aborting broadcast")
            abortBroadcast()
        }
    }

    fun addCurrentDeviceId(deviceId: String) {
        devicesList.add(deviceId)
        removeJob?.cancel()
    }

    fun removeCurrentDeviceId(deviceId: String) {
        startEmptyDelay()
    }

    private fun startEmptyDelay() {
        removeJob?.cancel()
        removeJob = launch {
            delay(20000)
            emptyDevicesList()
        }
    }

    private fun emptyDevicesList() {
        devicesList.clear()
        Log.d(TAG, "Clear devices")
    }
}