package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.DoorFront
import androidx.compose.material.icons.outlined.DoorSliding
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.DoorState
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Preview(showBackground = true)
@Composable
fun DoorDeviceInfoPreview() {
    DoorDeviceInfo(device = DoorDevice(DoorState()))
}

@Composable
fun DoorDeviceInfo(
    device: DoorDevice,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var locked by remember { mutableStateOf(device.state.isLocked) }
    var open by remember { mutableStateOf(device.state.isOpen) }

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
                        onClick = { /*TODO*/ },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !locked
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (open) Icons.Outlined.DoorFront else Icons.Outlined.DoorFront, //TODO DoorOpen
                                contentDescription = "Open",
                                tint = Color.White,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(text = if (open) "Close" else "Open")
                        }
                    }
                }

                Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                    Button(
                        onClick = { /*TODO*/ },
                        elevation = ButtonDefaults.buttonElevation(
                            20.dp, 10.dp, 10.dp, 10.dp, 0.dp
                        ),
                        shape = RoundedCornerShape(5.dp),
                        enabled = !disabled && !loading && !open
                    ) {
                        Column(
                            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = if (locked) Icons.Outlined.LockOpen else Icons.Outlined.Lock,
                                contentDescription = "Lock",
                                tint = Color.White,
                                modifier = Modifier.size(35.dp)
                            )
                            Text(text = "Lock")
                        }
                    }
                }
            }
        }
    }
}