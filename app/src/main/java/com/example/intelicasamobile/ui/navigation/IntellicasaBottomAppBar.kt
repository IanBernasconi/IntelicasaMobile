package com.example.intelicasamobile.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.intelicasamobile.model.Screen

@Composable
fun IntelicasaBottomAppBar(
    navController: NavController = rememberNavController(),
    screens: List<Screen>
) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(containerColor = MaterialTheme.colorScheme.tertiary) {
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
                    Text(text = screen.title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onTertiary)
                },
                icon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title, tint = MaterialTheme.colorScheme.onTertiary)
                }
            )
        }
    }
}
