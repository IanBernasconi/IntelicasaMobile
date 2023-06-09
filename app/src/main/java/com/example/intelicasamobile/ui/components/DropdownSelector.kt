package com.example.intelicasamobile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACMode

@Preview(showBackground = true)
@Composable
fun DropdownPreview() {
    val mode = ACMode.FAN
    val dropdownModeStateHolder = rememberDropdownSelectorState(items = ACMode.values().map {
        DropdownSelectorItem(
            label = stringResource(id = it.nameResId), value = it, icon = it.imageResourceId
        )
    }, label = "Modo", initialItem = DropdownSelectorItem(
        label = stringResource(id = mode.nameResId),
        value = mode,
        icon = mode.imageResourceId,
    ), onItemSelected = { })
    DropdownSelector(stateHolder = dropdownModeStateHolder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(stateHolder: DropdownSelectorStateHolder, modifier: Modifier = Modifier) {

    Box() {
        OutlinedTextField(value = stateHolder.value,
            onValueChange = {},
            label = { Text(text = stateHolder.label) },
            leadingIcon = {
                stateHolder.selectedItem?.icon?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = modifier
                            .size(50.dp)
                            .padding(dimensionResource(id = R.dimen.padding_small), 0.dp)
                    )
                }
            },
            trailingIcon = {
                Icon(imageVector = stateHolder.icon,
                    contentDescription = "Dropdown",
                    modifier = modifier
                        .size(35.dp)
                        .clickable { stateHolder.onExpanded(!(stateHolder.expanded)) })
            },
            modifier = modifier
                .padding(end = 4.dp)
                .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) }
                .clickable { stateHolder.onExpanded(!(stateHolder.expanded)) },
            readOnly = true
        )

        DropdownMenu(
            expanded = stateHolder.expanded,
            onDismissRequest = { stateHolder.onExpanded(false) },
            modifier = Modifier
                .padding(0.dp)
                .width(with(LocalDensity.current) { stateHolder.size.width.toDp() })
        ) {
            stateHolder.getItemsToDisplay().forEach { item ->
                DropdownMenuItem(onClick = {
                    stateHolder.onSelected(item)
                    stateHolder.onItemSelected(item)
                    stateHolder.onExpanded(false)
                }, text = {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        item.icon?.let {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(
                                        dimensionResource(id = R.dimen.padding_small), 0.dp
                                    )
                            )
                        }
                        Text(
                            text = item.label
                        )
                    }
                })
            }
        }
    }
}