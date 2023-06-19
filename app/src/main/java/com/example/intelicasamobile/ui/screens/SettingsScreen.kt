package com.example.intelicasamobile.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.persistent.Preference
import com.example.intelicasamobile.data.persistent.PreferencesData
import com.example.intelicasamobile.data.persistent.ThemePreferenceType
import com.example.intelicasamobile.dataStore
import com.example.intelicasamobile.ui.components.AnimatedCollapsibleItem

@Preview(showBackground = true)
@Composable
fun SettingsScreen(
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

            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE){
                AnimatedCollapsibleItem(title = stringResource(id = R.string.pref_key_theme)) {
                    ThemeSelector(preferences)
                }
            }else{
                Text(
                    text = stringResource(id = R.string.pref_key_theme),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                )
                ThemeSelector(preferences)
            }

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
        themePreferences.value.find { it.type == ThemePreferenceType.OVERRIDE_SYSTEM }?.let {
            ToggleRow(preference = it, textId = R.string.pref_theme_override) {
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


@Preview(showBackground = true)
@Composable
fun TabletSettingsScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        SettingsScreen(2)
    }
}