package com.example.intelicasamobile.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun IntellicasaBottomAppBar(
    navController: NavController = rememberNavController(),
    onHomeButtonClicked: () -> Unit = {},
    onDevicesButtonClicked: () -> Unit = {},
    onRoutinesButtonClicked: () -> Unit = {},
    onMenuButtonClicked: () -> Unit = {},
) {
    BottomAppBar() {
        Row() {
            Button(onClick = { onHomeButtonClicked()}) {
                Text(text = "Home")
            }
            Button(onClick = { onDevicesButtonClicked()}) {
                Text(text = "Devices")
            }
            Button(onClick = { onRoutinesButtonClicked() }) {
                Text(text = "Routines")
            }
            Button(onClick = { onMenuButtonClicked() }) {
                Text(text = "Menu")
            }
        }
    }
}
