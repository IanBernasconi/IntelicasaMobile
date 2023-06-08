package com.example.intelicasamobile

import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Device
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intelicasamobile.ui.DevicesScreen
import com.example.intelicasamobile.ui.HomeScreen
import com.example.intelicasamobile.ui.IntellicasaBottomAppBar
import com.example.intelicasamobile.ui.IntellicasaTopAppBar
import com.example.intelicasamobile.ui.MainViewModel
import com.example.intelicasamobile.ui.MenuScreen
import com.example.intelicasamobile.ui.RoutinesScreen
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntelicasaMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IntelicasaApp()
                }
            }
        }
    }
}

enum class Screens() {
    HOME,
    DEVICES,
    ROUTINES,
    MENU
}

enum class WindowsSizeClass { COMPACT, MEDIUM, EXPANDED }

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Intelicasa")
@Composable
fun IntelicasaApp(
    viewModel: MainViewModel = MainViewModel(),
    navController: NavHostController = rememberNavController(),
//    windowsSizeClass: WindowsSizeClass
) {

    IntelicasaMobileTheme {
        Scaffold(
            topBar = {
                IntellicasaTopAppBar()
            },
            bottomBar = {
                IntellicasaBottomAppBar(
                    navController = navController,
                    onHomeButtonClicked = {
                        navController.navigate(Screens.HOME.name)
                    },
                    onDevicesButtonClicked = {
                        navController.navigate(Screens.DEVICES.name)
                    },
                    onRoutinesButtonClicked = {
                        navController.navigate(Screens.ROUTINES.name)
                    },
                    onMenuButtonClicked = {
                        navController.navigate(Screens.MENU.name)
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.HOME.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screens.HOME.name) {
                    HomeScreen()
                }
                composable(route = Screens.DEVICES.name) {
                    DevicesScreen()
                }
                composable(route = Screens.ROUTINES.name) {
                    RoutinesScreen()
                }
                composable(route = Screens.MENU.name) {
                    MenuScreen()
                }
            }
        }
    }
}



