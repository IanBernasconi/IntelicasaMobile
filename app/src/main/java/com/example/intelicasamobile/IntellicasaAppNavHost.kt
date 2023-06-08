package com.example.intelicasamobile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Intelicasa")
@Composable
fun IntelicasaAppNavHost(
    viewModel: MainViewModel = MainViewModel(),
    navController: NavHostController = rememberNavController(),
//    windowsSizeClass: WindowsSizeClass
) {
    val screens = listOf(
        Screen("Home", "home", Icons.Filled.Home) { HomeScreen() },
        Screen("Devices", "devices", Icons.Filled.Bed){ DevicesScreen() },
        Screen("Routines", "routines", Icons.Filled.PlayArrow){ RoutinesScreen() },
        Screen("Menu", "menu", Icons.Filled.Menu){ MenuScreen() },
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


