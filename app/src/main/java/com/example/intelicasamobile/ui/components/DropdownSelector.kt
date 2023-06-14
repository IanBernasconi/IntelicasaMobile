package com.example.intelicasamobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    TextFieldDropdownSelector(stateHolder = dropdownModeStateHolder)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDropdownSelector(
    stateHolder: DropdownSelectorStateHolder, modifier: Modifier = Modifier
) {

    Box {
        OutlinedTextField(
            value = stateHolder.value,
            onValueChange = {},
            label = { Text(text = stateHolder.label) },
            leadingIcon = {
                stateHolder.selectedItem?.icon?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.image_size))
                            .padding(dimensionResource(id = R.dimen.padding_small), 0.dp)
                    )
                }
            },
            trailingIcon = {
                Icon(
                    imageVector = stateHolder.icon,
                    contentDescription = "Dropdown",
                    modifier = modifier.size(35.dp)
                )
            },
            modifier = modifier.onGloballyPositioned { stateHolder.onSize(it.size.toSize()) },
            readOnly = true
        )
        InnerDropdownSelector(stateHolder = stateHolder, modifier = modifier)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ShapeDropDownSelectorPreview() {
//
//    val dropdownRoomStateHolder = rememberDropdownSelectorState(items = Datasource.rooms.map {
//        DropdownSelectorItem(
//            label = it.name, value = it, icon = it.roomType.imageResourceId
//        )
//    }, onItemSelected = {}
//    )
//    ShapeDropdownSelector(stateHolder = dropdownRoomStateHolder)
//}

@Composable
fun ShapeDropdownSelector(
    stateHolder: DropdownSelectorStateHolder,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    fontSize: Int = 16
) {
    Box {
        Surface(
            modifier = modifier
                .clip(shape)
                .onGloballyPositioned { stateHolder.onSize(it.size.toSize()) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .clip(shape)
                    .background(color = color)

            ) {
                stateHolder.selectedItem?.icon?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = modifier
                            .size(dimensionResource(id = R.dimen.image_size))
                            .padding(dimensionResource(id = R.dimen.padding_small), 0.dp)
                    )
                }
                Text(
                    text = stateHolder.value,
                    color = textColor,
                    style = TextStyle(fontSize = fontSize.sp)
                )
                Icon(
                    imageVector = stateHolder.icon,
                    contentDescription = "Dropdown",
                    modifier = modifier.size(35.dp),
                    tint = textColor
                )

            }
        }
        InnerDropdownSelector(stateHolder = stateHolder, modifier = modifier)
    }
}

@Composable
fun InnerDropdownSelector(
    stateHolder: DropdownSelectorStateHolder, modifier: Modifier = Modifier
) {
    Button(
        onClick = { stateHolder.onExpanded(!(stateHolder.expanded)) },
        modifier = modifier
            .alpha(0f)
            .width(with(LocalDensity.current) { stateHolder.size.width.toDp() })
            .height(with(LocalDensity.current) { stateHolder.size.height.toDp() }),
        enabled = !stateHolder.loading
    ) {}

    DropdownMenu(
        expanded = stateHolder.expanded,
        onDismissRequest = { stateHolder.onExpanded(false) },
        modifier = Modifier
            .padding(0.dp)
            .width(with(LocalDensity.current) { stateHolder.size.width.toDp() }),
    ) {
        stateHolder.getItemsToDisplay().forEach { item ->
            DropdownMenuItem(onClick = {
                stateHolder.onSelected(item)
                stateHolder.onItemSelected(item)
                stateHolder.onExpanded(false)
            }, text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    item.icon?.let {
                        Image(
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
