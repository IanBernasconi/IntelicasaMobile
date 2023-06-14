package com.example.intelicasamobile.model






import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class Screen (
    val title: String,
    val route: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit,
    val tabletContent: @Composable () -> Unit = content
    )

