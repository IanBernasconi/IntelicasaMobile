package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoorFront
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.DoorState
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun DoorDeviceInfoPreview() {
    val device = DoorDevice()
    DoorDeviceInfo(
        state = device.state,
        setIsLocked = { },
        setIsOpen = { })
}

@Composable
fun DoorDeviceInfo(
    state: DoorState,
    setIsLocked: (Boolean) -> Unit,
    setIsOpen: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localIsLocked by remember { mutableStateOf(state.isLocked) }
    var localIsOpen by remember { mutableStateOf(state.isOpen) }

    IntelicasaMobileTheme() {
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                    Button(
                        onClick = { setIsOpen(!localIsOpen); localIsOpen = !localIsOpen },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !localIsLocked
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (localIsOpen) Icons.Outlined.DoorFront else Icons.Outlined.DoorFront, //TODO DoorOpen
                                contentDescription = if (localIsOpen) stringResource(id = R.string.DS_open) else stringResource(id = R.string.DS_closed),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(text = if (localIsOpen) stringResource(id = R.string.DA_open) else stringResource(id = R.string.DA_close))
                        }
                    }
                }

                Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                    Button(
                        onClick = { setIsLocked(!localIsLocked); localIsLocked = !localIsLocked },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !localIsOpen
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (localIsLocked) Icons.Outlined.LockOpen else Icons.Outlined.Lock,
                                contentDescription = if (localIsLocked) stringResource(id = R.string.DS_locked) else stringResource(id = R.string.DS_unlocked),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(text = if (localIsLocked) stringResource(id = R.string.DA_unlock) else stringResource(id = R.string.DA_lock))
                        }
                    }
                }
            }
        }
    }
}