package com.example.intelicasamobile.data.network

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.intelicasamobile.BuildConfig
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.MyIntent
import com.example.intelicasamobile.data.network.model.NetworkEvents
import com.example.intelicasamobile.data.persistent.NotificationType
import com.example.intelicasamobile.data.persistent.PreferencesData
import com.example.intelicasamobile.dataStore
import com.example.intelicasamobile.model.DeviceType
import com.example.intelicasamobile.model.LightDevice
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DeviceUpdateService : Service() {
    private lateinit var updateDevicesJob: Job
    private var devicesViewModel = DevicesViewModel.getInstance()
    private lateinit var notificationPrefs: PreferencesData

    companion object {
        private const val TAG = "DeviceUpdateService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationPrefs = PreferencesData.getInstance(dataStore)
        startPeriodicDeviceUpdates()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPeriodicDeviceUpdates()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun startPeriodicDeviceUpdates() {
        updateDevicesJob = GlobalScope.launch(Dispatchers.IO) {
            while (isActive) {
                delay(10000) // Delay for 10 seconds
                processEvents()
            }
        }
    }
    private suspend fun processEvents() {
        Log.d(TAG, "processEvents: ")
        val events = fetchEvents()
        events?.forEach {
            Log.d(TAG, "Event: $it")
            val device = devicesViewModel.fetchDevice(it.deviceId) ?: return@forEach
            Log.d(TAG, "Device: $device")
            var message = ""
            var title = ""
            var showNotification = false
            when (it.event) {
                "statusChanged" -> {
                    Log.d(TAG, "Status changed")
                    if (it.value["newStatus"] == "on" || it.value["newStatus"] == "off") {
                        val turnedOn = it.value["newStatus"] == "on"
                        when (device.deviceType) {
                            DeviceType.LAMP -> if (turnedOn) {
                                if (notificationPrefs.getLocalPreference(NotificationType.LIGHT_ON.value)) {
                                    title = getString(R.string.lamp_on_title)
                                    message = getString(R.string.lamp_on_info, device.name)
                                    showNotification = true
                                }
                            } else {
                                if (notificationPrefs.getLocalPreference(NotificationType.LIGHT_OFF.value)) {
                                    title = getString(R.string.lamp_off_title)
                                    message = getString(R.string.lamp_off_info, device.name)
                                    showNotification = true
                                }
                            }

                            DeviceType.AIR_CONDITIONER -> if (turnedOn) {
                                if (notificationPrefs.getLocalPreference(NotificationType.AC_ON.value)) {
                                    title = getString(R.string.ac_on_title)
                                    message = getString(R.string.ac_on_info, device.name)
                                    showNotification = true
                                }
                            } else {
                                if (notificationPrefs.getLocalPreference(NotificationType.AC_OFF.value)) {
                                    title = getString(R.string.ac_off_title)
                                    message = getString(R.string.ac_off_info, device.name)
                                    showNotification = true
                                }
                            }

                            DeviceType.OVEN -> if (turnedOn) {
                                if (notificationPrefs.getLocalPreference(NotificationType.OVEN_ON.value)) {
                                    title = getString(R.string.oven_on_title)
                                    message = getString(R.string.oven_on_info, device.name)
                                    showNotification = true
                                }
                            } else {
                                if (notificationPrefs.getLocalPreference(NotificationType.OVEN_OFF.value)) {
                                    title = getString(R.string.oven_off_title)
                                    message = getString(R.string.oven_off_info, device.name)
                                    showNotification = true
                                }
                            }

                            else -> {}
                        }
                    } else if (it.value["newStatus"] == "opened" || it.value["newStatus"] == "closed") {
                        val open = it.value["newStatus"] == "opened"
                        if (open) {
                            if (notificationPrefs.getLocalPreference(NotificationType.DOOR_OPEN.value)) {
                                title = getString(R.string.door_opened_title)
                                message = getString(R.string.door_opened_info, device.name)
                                showNotification = true
                            }
                        } else {
                            if (notificationPrefs.getLocalPreference(NotificationType.DOOR_CLOSE.value)) {
                                title = getString(R.string.door_closed_title)
                                message = getString(R.string.door_closed_info, device.name)
                                showNotification = true
                            }
                        }
                    } else if (it.value["newStatus"] == "inactive" || it.value["newStatus"] == "active" || it.value["newStatus"] == "docked") {
                        if (device.deviceType == DeviceType.VACUUM_CLEANER){
                           when(it.value["newStatus"]){
                                 "inactive" -> {
                                      if (notificationPrefs.getLocalPreference(NotificationType.VACUUM_OFF.value)) {
                                        title = getString(R.string.vacuum_paused_title)
                                        message = getString(R.string.vacuum_paused_info, device.name)
                                        showNotification = true
                                      }
                                 }
                                 "docked" -> {
                                      if (notificationPrefs.getLocalPreference(NotificationType.VACUUM_ON.value)) {
                                        title = getString(R.string.vacuum_charging_title)
                                        message = getString(R.string.vacuum_charging_info, device.name)
                                        showNotification = true
                                      }
                                 }
                                 "active" -> {
                                      if (notificationPrefs.getLocalPreference(NotificationType.VACUUM_DOCK.value)) {
                                        title = getString(R.string.vacuum_cleaning_title)
                                        message = getString(R.string.vacuum_cleaning_info, device.name)
                                        showNotification = true
                                      }
                                 }
                           }
                        }
                    }
                }
                "lockChanged" -> {
                    Log.d(TAG, "New lock")
                    val locked = it.value["newLock"] == "locked"
                    if (locked) {
                        if (notificationPrefs.getLocalPreference(NotificationType.DOOR_LOCK.value)) {
                            title = getString(R.string.door_locked_title)
                            message = getString(R.string.door_locked_info, device.name)
                            showNotification = true
                        }
                    } else {
                        if (notificationPrefs.getLocalPreference(NotificationType.DOOR_UNLOCK.value)) {
                            title = getString(R.string.door_unlocked_title)
                            message = getString(R.string.door_unlocked_info, device.name)
                            showNotification = true
                        }
                    }
                }
                // TODO add vacuum events. Find out event names
                else -> {}
            }

            if (showNotification) {
                //  showNotification(context = applicationContext, title, message, showNotification)
                Log.d(TAG, "showNotification: $title $message")
                val intent = Intent().apply {
                    action = MyIntent.SHOW_NOTIFICATION
                    `package` = MyIntent.PACKAGE
                    putExtra(MyIntent.DEVICE_ID, it.deviceId)
                    putExtra(MyIntent.NOTIFICATION_TITLE, title)
                    putExtra(MyIntent.NOTIFICATION_MESSAGE, message)
                }

                sendOrderedBroadcast(intent, null)
                showNotification = false
            }
        }
    }

    private fun fetchEvents(): List<NetworkEvents>? {
        Log.d(TAG, "fetchEvents: Fetching events")

        val url = "${BuildConfig.API_BASE_URL}api/devices/events"

        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Content-Type", "text/event-stream")
            doInput = true
        }

        Log.d(TAG, "fetchEvents: Response code ${connection.responseCode}")

        val responseCode = connection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<NetworkEvents>()
            while (stream.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }

                    line!!.isEmpty() -> {
                        val gson = Gson()
                        val event = gson.fromJson(response.toString(), NetworkEvents::class.java)
                        eventList.add(event)
                        Log.d(TAG, "fetchEvents: Event: $response")
                        response.setLength(0)
                    }
                }
            }

            stream.close()
            eventList
        } else {
            null
        }
    }

    private fun stopPeriodicDeviceUpdates() {
        if (updateDevicesJob.isActive) updateDevicesJob.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
