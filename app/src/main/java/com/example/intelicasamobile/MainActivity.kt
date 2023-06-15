package com.example.intelicasamobile

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
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.ui.navigation.IntelicasaAppNavHost
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val devicesModel by remember { mutableStateOf(DevicesViewModel()) }
            val routinesModel by remember { mutableStateOf(RoutinesViewModel()) }
            val roomsModel by remember { mutableStateOf(RoomsViewModel()) }

            LaunchedEffect(Unit) {
                devicesModel.fetchDevices()
                routinesModel.fetchRoutines()
                roomsModel.fetchRooms()
            }

            IntelicasaMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    IntelicasaAppNavHost(windowSize = windowSize.widthSizeClass, devicesModel = devicesModel, routinesModel = routinesModel, roomsModel = roomsModel)
                }
            }
        }
    }
}