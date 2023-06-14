package com.example.intelicasamobile.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.intelicasamobile.MyNotification
import com.example.intelicasamobile.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MenuScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            DialogButton(R.string.help, R.string.help_text)
            DialogButton(R.string.contact, R.string.contact_text)
            DialogButton(R.string.about, R.string.about_text)

        }
    }
}

@Composable
fun DialogButton(@StringRes title: Int, @StringRes text: Int, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
        modifier = modifier
            .clickable { showDialog = true }
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large)
            )
    ) {
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
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = modifier.background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
            ) {
                Card(modifier = modifier) {
                    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                        Text(
                            text = stringResource(id = text),
                            modifier = modifier.padding(
                                horizontal = dimensionResource(id = R.dimen.padding_medium),
                                vertical = dimensionResource(
                                    id = R.dimen.padding_medium
                                ),
                            ).fillMaxWidth(),
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
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ) {
            DialogButton(R.string.help, R.string.help_text)
            DialogButton(R.string.contact, R.string.contact_text)
            DialogButton(R.string.about, R.string.about_text)

        }
    }
}

@Composable
fun NotificationButton() {
    val context = LocalContext.current
    Button(onClick = {
        val notification =
            MyNotification(context = context, title = "Test Title", message = "Test Body")
        notification.fireNotification()
    }) {
        Text(text = "Send Notification")
    }
}

