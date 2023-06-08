package com.example.intelicasamobile.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector

class DropdownSelectorItem(
    val label: String,
    val value: Any,
    @DrawableRes val icon: Int? = null,
)

class DropdownSelectorStateHolder(
    val items: List<DropdownSelectorItem>,
    val label: String,
    private val initialItem: DropdownSelectorItem? = null,
    val onItemSelected: (DropdownSelectorItem) -> Unit,
) {
    var enabled by mutableStateOf(true)
    var value by mutableStateOf(initialItem?.label ?: "")
    var expanded by mutableStateOf(false)
    var selectedIndex by mutableStateOf(0)
    var size by mutableStateOf(Size.Zero)
    val icon: ImageVector
        @Composable get() = if (enabled) {
            Icons.Filled.ArrowDropDown
        } else {
            Icons.Filled.ArrowDropDown
        }

    fun onEnabled(newValue: Boolean) {
        enabled = newValue
    }

    fun onSelectedIndex(newValue: Int) {
        selectedIndex = newValue
        value = items[newValue].label
    }

    fun onSize(newValue: Size) {
        size = newValue
    }
}

@Composable
fun rememberDropdownSelectorState(
    items: List<DropdownSelectorItem>,
    label: String,
    initialItem: DropdownSelectorItem? = null,
    onItemSelected: (DropdownSelectorItem) -> Unit
) = remember {
    DropdownSelectorStateHolder(items, label, initialItem, onItemSelected)
}