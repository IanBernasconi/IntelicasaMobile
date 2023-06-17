package com.example.intelicasamobile

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.MyIntent
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.data.network.SkipNotificationReceiver
import com.example.intelicasamobile.data.persistent.PreferencesData
import com.example.intelicasamobile.ui.navigation.IntelicasaAppNavHost
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
lateinit var receiver: SkipNotificationReceiver

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getIntExtra("deviceID", -1)

        setContent {
            val preferences = PreferencesData.getInstance(dataStore)

            val devicesModel by remember { mutableStateOf(DevicesViewModel.getInstance()) }
            val routinesModel by remember { mutableStateOf(RoutinesViewModel()) }
            val roomsModel by remember { mutableStateOf(RoomsViewModel()) }

            LaunchedEffect(Unit) {
                devicesModel.fetchDevices()
                routinesModel.fetchRoutines()
                roomsModel.fetchRooms()
                preferences.loadPreferences()
            }

            IntelicasaMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    IntelicasaAppNavHost(windowSize = windowSize.widthSizeClass, devicesModel = devicesModel, routinesModel = routinesModel, roomsModel = roomsModel)
                }
            }
        }

        receiver = SkipNotificationReceiver()
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also { registerReceiver(receiver, it) }

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}