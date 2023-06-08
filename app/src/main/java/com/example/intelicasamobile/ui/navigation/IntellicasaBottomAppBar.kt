package com.example.intelicasamobile.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intelicasamobile.model.Screen
import com.example.intelicasamobile.ui.DevicesScreen
import com.example.intelicasamobile.ui.HomeScreen
import com.example.intelicasamobile.ui.MenuScreen
import com.example.intelicasamobile.ui.RoutinesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun IntellicasaBottomAppBar(
    navController: NavController = rememberNavController(),
    onHomeButtonClicked: () -> Unit = {},
    onDevicesButtonClicked: () -> Unit = {},
    onRoutinesButtonClicked: () -> Unit = {},
    onMenuButtonClicked: () -> Unit = {},
    screens: List<Screen> = listOf(
        Screen("Home", "home", Icons.Filled.Home){ HomeScreen() },
        Screen("Devices", "devices", Icons.Filled.Menu){ DevicesScreen() },
        Screen("Routines", "routines", Icons.Filled.Menu){ RoutinesScreen() },
        Screen("Menu", "menu", Icons.Filled.Menu){ MenuScreen() },
    )
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        screens.forEach { screen ->
            val selected = screen.route == backStackEntry?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(screen.route){
                    navController.graph.startDestinationRoute?.let { route->
                        popUpTo(route){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                } },
                label = {
                    Text(text = screen.title, fontWeight = FontWeight.SemiBold)
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                }
            )
        }
    }
}
