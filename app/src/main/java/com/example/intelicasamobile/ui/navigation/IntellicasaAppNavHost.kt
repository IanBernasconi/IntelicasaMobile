package com.example.intelicasamobile.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intelicasamobile.model.AppNavigationType
import com.example.intelicasamobile.model.Screen
import com.example.intelicasamobile.ui.HomeScreen
import com.example.intelicasamobile.ui.MainViewModel
import com.example.intelicasamobile.ui.MenuScreen
import com.example.intelicasamobile.ui.RoomsScreen
import com.example.intelicasamobile.ui.RoutinesScreen
import com.example.intelicasamobile.ui.TabletHomeScreen
import com.example.intelicasamobile.ui.TabletMenuScreen
import com.example.intelicasamobile.ui.TabletRoomsScreen
import com.example.intelicasamobile.ui.TabletRoutinesScreen
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Intelicasa")
@Composable
fun IntelicasaAppNavHost(
    viewModel: MainViewModel = MainViewModel(),
    navController: NavHostController = rememberNavController(),
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
) {
    val screens = listOf(
        Screen("Home", "home", Icons.Filled.Home, tabletContent = { TabletHomeScreen() }, content =  { HomeScreen() }),
        Screen("Devices", "devices", Icons.Filled.Bed, tabletContent = { TabletRoomsScreen() }, content =  { RoomsScreen() }),
        Screen("Routines", "routines", Icons.Filled.PlayArrow, tabletContent = { TabletRoutinesScreen()}, content =  { RoutinesScreen() }),
        Screen("Menu", "menu", Icons.Filled.Menu, tabletContent = { TabletMenuScreen()}, content =  { MenuScreen() })
    )


    val navigationType: AppNavigationType
    val backStackEntry by navController.currentBackStackEntryAsState()
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = AppNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
        }
    }
    IntelicasaMobileTheme {
        Scaffold(topBar = {
            IntellicasaTopAppBar()
        }, bottomBar = {
            when (navigationType) {
                AppNavigationType.BOTTOM_NAVIGATION -> {
                    IntellicasaBottomAppBar(
                        navController = navController, screens = screens
                    )
                }

                AppNavigationType.PERMANENT_NAVIGATION_DRAWER -> {

                    PermanentNavigationDrawer(drawerContent = {
                        IntellicasaAppNavigationDrawer(
                            screens = screens,
                            navController = navController,
                            backStackEntry = backStackEntry
                        )
                    }
                    ){
                        AppNavHost(navController, screens,windowSize)
                    }
                }
            }
        }) { innerPadding ->
            AppNavHost(navController, screens,windowSize, modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    screens: List<Screen>,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    modifier: Modifier = Modifier

) {
    NavHost(
        navController = navController, startDestination = screens.first().route, modifier = modifier
    ) {
        screens.forEach { screen ->
            composable(route = screen.route) {
                when (windowSize) {
                    WindowWidthSizeClass.Compact -> {
                        screen.content()
                    }

                    WindowWidthSizeClass.Medium -> {
                        screen.content()
                    }

                    WindowWidthSizeClass.Expanded -> {
                        screen.tabletContent()
                    }

                    else -> {
                        screen.content()
                    }
                }
            }
        }
    }
}




