package com.example.intelicasamobile.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector

class DropdownSelectorItem(
    val label: String = "",
    val value: Any,
    @DrawableRes val icon: Int? = null,
)

class DropdownSelectorStateHolder(
    private val items: List<DropdownSelectorItem>,
    val label: String,
    initialItem: DropdownSelectorItem? = null,
    val onItemSelected: (DropdownSelectorItem) -> Unit,
    val loading: Boolean = false,
) {
    var value by mutableStateOf(initialItem?.label ?: "")
    var expanded by mutableStateOf(false)
    var selectedItem by mutableStateOf(initialItem)
    var size by mutableStateOf(Size.Zero)
    val icon: ImageVector
        @Composable get() = if (expanded) {
            Icons.Filled.ArrowDropUp
        } else {
            Icons.Filled.ArrowDropDown
        }

    fun onExpanded(newValue: Boolean) {
        expanded = newValue
    }

    fun onSelected(newValue: DropdownSelectorItem) {
        selectedItem = newValue
        value = newValue.label
    }

    fun onSize(newValue: Size) {
        size = newValue
    }

    fun getItemsToDisplay() = items.filter { it.value != selectedItem?.value }
}

@Composable
fun rememberDropdownSelectorState(
    items: List<DropdownSelectorItem>,
    label: String = "",
    initialItem: DropdownSelectorItem? = null,
    onItemSelected: (DropdownSelectorItem) -> Unit,
    loading: Boolean = false,
) = remember {
     DropdownSelectorStateHolder(items, label, initialItem, onItemSelected, loading)
}