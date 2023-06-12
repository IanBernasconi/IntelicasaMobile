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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun DoorDeviceInfoPreview() {
    val device = DoorDevice(deviceId = "1", deviceName = "Door")
    DoorDeviceInfo(
        state = device
    )
}

@Composable
fun DoorDeviceInfo(
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
    state: DoorDevice = viewModel(),
) {

    val uiState by state.state.collectAsState()

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
                        onClick = { state.setOpen(!uiState.isOpen) },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !uiState.isLocked
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (uiState.isOpen) Icons.Outlined.DoorFront else Icons.Outlined.DoorFront, //TODO DoorOpen
                                contentDescription = if (uiState.isOpen) stringResource(id = R.string.DS_closed) else stringResource(
                                    id = R.string.DS_open
                                ),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(
                                text = if (uiState.isOpen) stringResource(id = R.string.DA_close) else stringResource(
                                    id = R.string.DA_open
                                )
                            )
                        }
                    }
                }

                Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                    Button(
                        onClick = { state.setLocked(!uiState.isLocked) },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !uiState.isOpen
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (uiState.isLocked) Icons.Outlined.LockOpen else Icons.Outlined.Lock,
                                contentDescription = if (uiState.isLocked) stringResource(id = R.string.DS_locked) else stringResource(
                                    id = R.string.DS_unlocked
                                ),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(
                                text = if (uiState.isLocked) stringResource(id = R.string.DA_unlock) else stringResource(
                                    id = R.string.DA_lock
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}