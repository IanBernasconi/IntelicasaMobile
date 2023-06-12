package com.example.intelicasamobile.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Screen

@Composable
fun IntelicasaNavigationRail(
    navController: NavHostController,
    screens: List<Screen>,
    backStackEntry: NavBackStackEntry? = null,
    content: @Composable () -> Unit
) {

    Row() {
        NavigationRail() {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.logo_size))
                    .padding(
                        dimensionResource(id = R.dimen.padding_small),
                        dimensionResource(id =R.dimen.padding_small),
                        dimensionResource(id = R.dimen.padding_small),
                        dimensionResource(id = R.dimen.padding_medium)),

                painter = painterResource(R.drawable.logointeli),
                contentDescription = null
            )
            screens.forEach { screen ->
                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        ) },
                    label = {
                        Text(
                            text = screen.title,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold)
                    },
                    selected = backStackEntry?.destination?.route == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        content()
    }
}