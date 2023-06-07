package com.example.intelicasamobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.intelicasamobile.model.ACMode

@Preview(showBackground = true)
@Composable
fun DropdownPreview() {
    val mode = ACMode.FAN
    val dropdownModeStateHolder = rememberDropdownSelectorState(
        items = ACMode.values().map {
            DropdownSelectorItem(
                label = stringResource(id = it.nameResId),
                value = it,
                icon = it.imageResourceId
            )
        }, label = "Modo", initialItem =
        DropdownSelectorItem(
            label = stringResource(id = mode.nameResId),
            value = mode,
            icon = mode.imageResourceId
        )
    )
    DropdownSelector(stateHolder = dropdownModeStateHolder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(stateHolder: DropdownSelectorStateHolder) {
    Box() {
        OutlinedTextField(
            value = stateHolder.value,
            onValueChange = {},
            label = { Text(text = stateHolder.label) },
            trailingIcon = {
                Icon(
                    imageVector = stateHolder.icon,
                    contentDescription = "Dropdown",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { stateHolder.onEnabled(!(stateHolder.enabled)) }
                )
            },
            modifier = Modifier
                .padding(end = 4.dp)
                .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) },
        )

        DropdownMenu(
            expanded = stateHolder.expanded,
            onDismissRequest = { stateHolder.onEnabled(false) },
            modifier = Modifier
                .padding(0.dp)
                .width(with(LocalDensity.current) { stateHolder.size.width.toDp() })
        ) {
            stateHolder.items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    stateHolder.onSelectedIndex(index)
                    stateHolder.onEnabled(false)
                },
                    text = {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            item.icon?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(35.dp)
                                )
                            }

                            Text(
                                text = item.label
                            )
                        }
                    }
                )
            }
        }
    }
}