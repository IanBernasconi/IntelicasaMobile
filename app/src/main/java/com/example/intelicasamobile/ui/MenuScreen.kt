package com.example.intelicasamobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
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
            modifier= Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(text = "Menu", style = MaterialTheme.typography.bodyLarge)
            NotificationButton()
        }

    }
}

@Composable
fun NotificationButton() {
    val context = LocalContext.current
    Button(onClick = {
        val notification = MyNotification(context=context, title="Test Title", message = "Test Body")
        notification.fireNotification()
    }) {
        Text(text = "Send Notification")
    }
}