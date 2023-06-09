package com.example.intelicasamobile.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntellicasaAppNavigationDrawer(
    screens: List<Screen>,
    navController: NavHostController,
    backStackEntry: NavBackStackEntry? = null,
) {
    PermanentDrawerSheet(
        modifier = Modifier.width(dimensionResource(id = R.dimen.drawer_width))
    ) {
        screens.forEach { screen ->
            NavigationDrawerItem(icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                selected = backStackEntry?.destination?.route == screen.route,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start)
            )
        }
    }
}