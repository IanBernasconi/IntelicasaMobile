package com.example.intelicasamobile.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.persistent.PreferencesData
import com.example.intelicasamobile.data.persistent.Preference
import com.example.intelicasamobile.data.persistent.ThemePreferenceType
import com.example.intelicasamobile.dataStore
import com.example.intelicasamobile.ui.components.AnimatedCollapsibleItem
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun MenuScreen(
    columns: Int = 1,
) {

    val preferences = PreferencesData.getInstance(LocalContext.current.dataStore)

    LaunchedEffect(Unit) {
        preferences.loadPreferences()
    }

        Column {
            Text(
                text = stringResource(id = R.string.pref_title),
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )

            ThemeSelector(preferences)

            AnimatedCollapsibleItem(title = stringResource(id = R.string.pref_key_notifications)) {
                NotificationPreferencesOptions(preferences, columns)
            }

    }
}

@Composable
fun ThemeSelector(
    preferences: PreferencesData
) {
    val themePreferences = preferences.themePreferences.collectAsState()

    Column {
        Text(
            text = stringResource(id = R.string.pref_key_theme),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )
        themePreferences.value.find { it.type == ThemePreferenceType.OVERRIDE_SYSTEM }?.let {
            ToggleRow(preference = it, textId = R.string.pref_theme_override, inverted = true) {
                preferences.setThemePreference(it.type.value, !it.getValue())
            }
        }

        themePreferences.value.find { it.type == ThemePreferenceType.DARK }
            ?.let { themePreference ->
                ToggleRow(preference = themePreference,
                    textId = R.string.pref_theme_dark,
                    enabled = themePreferences.value.find { it.type == ThemePreferenceType.OVERRIDE_SYSTEM }
                        ?.getValue() == true) {
                    preferences.setThemePreference(
                        themePreference.type.value, !themePreference.getValue()
                    )
                }
            }
    }
}

@Composable
fun NotificationPreferencesOptions(
    preferences: PreferencesData,
    columns: Int = 1
) {
    val notificationPreferences = preferences.notificationPreference.collectAsState()

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        ) {
            items(notificationPreferences.value) { preference ->
                ToggleRow(preference, preference.type.nameResId) {
                    preferences.setNotificationPreference(
                        preference.type.value, !preference.getValue()
                    )
                }
            }
        }
    }
}

@Composable
fun ToggleRow(
    preference: Preference,
    textId: Int,
    enabled: Boolean = true,
    inverted: Boolean = false,
    toggleChange: () -> Unit,
) {
    val localValue by remember { mutableStateOf(preference.value) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = textId), style = TextStyle(fontSize = 18.sp)
        )
        Switch(
            checked = localValue.value,
            onCheckedChange = { toggleChange() },
            enabled = !preference.loading.value && enabled
        )
    }
}

@Composable
fun DialogButton(@StringRes title: Int, @StringRes text: Int, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    Card(elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
        modifier = modifier
            .clickable { showDialog = true }
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large)
            )) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(id = title),
                modifier = modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_small),
                    vertical = dimensionResource(
                        id = R.dimen.padding_medium
                    ),
                ),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary

            )
        }
    }
    if (showDialog) {
        val drawerWidth = dimensionResource(id = R.dimen.drawer_width)

        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = modifier.background(Color.Transparent)
//                    .padding(start = drawerWidth)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(modifier = modifier.fillMaxWidth()) {
                    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                        Text(
                            text = stringResource(id = text),
                            modifier = modifier
                                .padding(dimensionResource(id = R.dimen.padding_medium))
                                .fillMaxWidth(),
                            style = TextStyle(fontSize = 20.sp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun TabletMenuScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        MenuScreen(2)
    }
}