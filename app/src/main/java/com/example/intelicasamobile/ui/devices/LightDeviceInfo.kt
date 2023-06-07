package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Preview
@Composable
fun LightDeviceInfoPreview() {
    LightDeviceInfo(device = LightDevice())
}

@Composable
fun LightDeviceInfo(
    device: LightDevice,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var intensity by remember { mutableStateOf(device.state.brightness) }
    var localColor by remember { mutableStateOf(device.state.color) }
    var showColorPicker by remember { mutableStateOf(false) }
    val colorController = rememberColorPickerController()
    IntelicasaMobileTheme() {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(modifier = modifier) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    StateInfo(isOn = device.state.isOn)
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                        Text(text = "Intensidad", style = TextStyle(fontSize = 16.sp))
                    }

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.width(90.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Transparent
                        ) {
                            Slider(
                                value = intensity.toFloat(),
                                onValueChange = { value ->
                                    intensity = value.toInt()
                                },
                                steps = 1,
                                enabled = !(disabled || loading),
                                modifier = Modifier.fillMaxWidth(),
                                colors = SliderDefaults.colors(
                                    thumbColor = MaterialTheme.colorScheme.secondary,
                                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.background
                                )
                            )
                        }
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                        Text(text = "Color", style = TextStyle(fontSize = 16.sp))
                    }

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { showColorPicker = true },
                            modifier = Modifier
                                .size(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = localColor)
                        ) {}

                        if (showColorPicker) {
                            AlertDialog(
                                onDismissRequest = { showColorPicker = false },
                                confirmButton = {
                                    HsvColorPicker(
                                        modifier = Modifier
                                            .width(250.dp)
                                            .height(250.dp)
                                            .padding(5.dp),
                                        controller = colorController,
                                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                                            val color: Color = colorEnvelope.color
                                            val hexCode: String = colorEnvelope.hexCode
                                            localColor = color
                                            showColorPicker = false
                                        }
                                    )
                                },
                                modifier = Modifier.padding(0.dp)
                            )

                        }
                    }
                }
            }
        }
    }
}