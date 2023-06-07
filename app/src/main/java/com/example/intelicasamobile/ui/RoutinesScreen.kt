package com.example.intelicasamobile.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RoutinesScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Text(text = "Routines")
    }
}