package com.example.intelicasamobile.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.intelicasamobile.IntelicasaApplication
import com.example.intelicasamobile.MainActivity
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntelicasaAppNavigationDrawer(
    screens: List<Screen>,
    navController: NavHostController,
    backStackEntry: NavBackStackEntry? = null,
) {
    PermanentDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.drawer_width))
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            Image(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.logo_size))
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                painter = painterResource(R.drawable.logointeli),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium
            )
        }
        screens.forEach { screen ->
            NavigationDrawerItem(
                shape= RectangleShape,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    unselectedContainerColor = MaterialTheme.colorScheme.tertiary,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onTertiary,
                    selectedBadgeColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedBadgeColor = MaterialTheme.colorScheme.onTertiary,

                    ),
                icon = {
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
                    IntelicasaApplication.currentPath = screen.route
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