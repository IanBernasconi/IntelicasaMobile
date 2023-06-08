package com.example.intelicasamobile

import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
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
import com.example.intelicasamobile.model.Screen
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


enum class WindowsSizeClass { COMPACT, MEDIUM, EXPANDED }

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Intelicasa")
@Composable
fun IntelicasaApp(
    viewModel: MainViewModel = MainViewModel(),
    navController: NavHostController = rememberNavController(),
//    windowsSizeClass: WindowsSizeClass
) {
    val screens = listOf(
        Screen("Home", "home", Icons.Filled.Home) { HomeScreen() },
        Screen("Devices", "devices", Icons.Filled.Home){ DevicesScreen() },
        Screen("Routines", "routines", Icons.Filled.Home){ RoutinesScreen()},
        Screen("Menu", "menu", Icons.Filled.Menu){ MenuScreen()},
    )

    IntelicasaMobileTheme {
        Scaffold(
            topBar = {
                IntellicasaTopAppBar()
            },
            bottomBar = {
                IntellicasaBottomAppBar(
                    navController = navController,
                    screens = screens
                )
            }
        ) {innerPadding->
            NavHost(
                navController = navController,
                startDestination = screens.first().route,
                modifier= Modifier.padding(innerPadding)
            ){
                screens.forEach{screen->
                    composable(route= screen.route){
                        screen.content()
                    }
                }
            }
        }
    }
}







