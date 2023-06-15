package com.example.intelicasamobile.data.network

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.intelicasamobile.data.DevicesViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DeviceUpdateService : Service(), CoroutineScope {
    private lateinit var updateDevicesJob: Job
    private var devicesViewModel = DevicesViewModel.getInstance()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startPeriodicDeviceUpdates()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPeriodicDeviceUpdates()
    }

    private fun startPeriodicDeviceUpdates() {
        updateDevicesJob = launch {
            while (isActive) {
                delay(10000) // Delay for 1 minute
                devicesViewModel.fetchDevices(context = applicationContext)
            }
        }
    }

    private fun stopPeriodicDeviceUpdates() {
        updateDevicesJob.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
