package com.example.intelicasamobile.ui.navigation

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.model.AppNavigationType
import com.example.intelicasamobile.model.Screen
import com.example.intelicasamobile.ui.screens.HomeScreen
import com.example.intelicasamobile.ui.screens.MenuScreen
import com.example.intelicasamobile.ui.screens.RoomsScreen
import com.example.intelicasamobile.ui.screens.RoutinesScreen
import com.example.intelicasamobile.ui.screens.TabletHomeScreen
import com.example.intelicasamobile.ui.screens.TabletMenuScreen
import com.example.intelicasamobile.ui.screens.TabletRoomsScreen
import com.example.intelicasamobile.ui.screens.TabletRoutinesScreen
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Intelicasa")
@Composable
fun IntelicasaAppNavHost(
    devicesModel: DevicesViewModel = viewModel(),
    routinesModel: RoutinesViewModel = viewModel(),
    roomsModel: RoomsViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
) {

    val screens = listOf(Screen("Home", "home", Icons.Filled.Home, tabletContent = {
        TabletHomeScreen(
            devicesModel = devicesModel,
            routinesModel = routinesModel,
            roomsModel = roomsModel
        )
    }, content = {
        HomeScreen(
            devicesModel = devicesModel,
            routinesModel = routinesModel,
            roomsModel = roomsModel
        )
    }),
        Screen("Devices", "devices", Icons.Filled.Bed, tabletContent = {
            TabletRoomsScreen(
                devicesModel = devicesModel, roomsModel = roomsModel
            )
        }, content = { RoomsScreen(devicesModel = devicesModel, roomsModel = roomsModel) }),
        Screen("Routines", "routines", Icons.Filled.PlayArrow, tabletContent = {
            TabletRoutinesScreen(
                routinesModel = routinesModel, devicesModel = devicesModel
            )
        }, content = {
            RoutinesScreen(
                routinesModel = routinesModel, devicesModel = devicesModel
            )
        }),
        Screen("Menu",
            "menu",
            Icons.Filled.Menu,
            tabletContent = { TabletMenuScreen() },
            content = { MenuScreen() })
    )
    val navigationType: AppNavigationType
    val backStackEntry by navController.currentBackStackEntryAsState()
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType =
                if (orientation == Configuration.ORIENTATION_PORTRAIT) AppNavigationType.BOTTOM_NAVIGATION
                else AppNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Medium -> {
            navigationType =
                if (orientation == Configuration.ORIENTATION_PORTRAIT) AppNavigationType.BOTTOM_NAVIGATION
                else AppNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = AppNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            navigationType = AppNavigationType.BOTTOM_NAVIGATION
        }
    }

    Scaffold(topBar = {
        IntelicasaTopAppBar()
    }, bottomBar = {
        when (navigationType) {
            AppNavigationType.BOTTOM_NAVIGATION -> {
                IntelicasaBottomAppBar(navController = navController, screens = screens)
            }

            AppNavigationType.NAVIGATION_RAIL -> {
                IntelicasaNavigationRail(
                    navController = navController,
                    screens = screens,
                    backStackEntry = backStackEntry
                ) {
                    AppNavHost(
                        navController = navController, screens = screens, windowSize = windowSize
                    )
                }
            }

            AppNavigationType.PERMANENT_NAVIGATION_DRAWER -> {

                PermanentNavigationDrawer(drawerContent = {
                    IntelicasaAppNavigationDrawer(
                        screens = screens,
                        navController = navController,
                        backStackEntry = backStackEntry
                    )
                }) {
                    AppNavHost(
                        navController = navController, screens = screens, windowSize = windowSize
                    )
                }
            }


        }
    }) { innerPadding ->
        AppNavHost(
            navController = navController,
            screens = screens,
            windowSize = windowSize,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
private fun AppNavHost(
    navController: NavHostController,
    screens: List<Screen>,
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact

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
                        screen.tabletContent()
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




