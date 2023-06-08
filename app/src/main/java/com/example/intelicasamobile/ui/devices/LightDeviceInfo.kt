package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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

@Preview(showBackground = true)
@Composable
fun LightDeviceInfoPreview() {
    val device = LightDevice()
    LightDeviceInfo(
        brightness = device.state.brightness,
        color = device.state.color,
        isOn = device.state.isOn,
        setBrightness = { },
        setColor = { },
        setIsOn = { }
    )
}

@Composable
fun LightDeviceInfo(
    brightness: Int,
    color: Color,
    isOn: Boolean,
    setBrightness: (Int) -> Unit,
    setColor: (Color) -> Unit,
    setIsOn: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localIntensity by remember { mutableStateOf(brightness) }
    var localColor by remember { mutableStateOf(color) }
    val colorController = rememberColorPickerController()
    IntelicasaMobileTheme() {
        Column(modifier = modifier) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StateInfo(
                    isOn = isOn,
                    setIsOn = setIsOn,
                    modifier = modifier,
                    disabled = disabled,
                    loading = loading
                )
            }


            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = dimensionResource(id = R.dimen.padding_large)),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Intensidad",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Slider(
                            value = localIntensity.toFloat(),
                            onValueChange = { localIntensity = it.toInt() },
                            onValueChangeFinished = { setBrightness(localIntensity) },
                            steps = 0,
                            enabled = !(disabled || loading),
                            modifier = Modifier.width(150.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.secondary
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
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = dimensionResource(id = R.dimen.padding_large)),
                    horizontalAlignment = Alignment.Start
                ) {
                    HsvColorPicker(modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                        .padding(5.dp),
                        controller = colorController,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            localColor = colorEnvelope.color
                            setColor(colorEnvelope.color)
                        }
                    )
                }
            }
        }
    }
}