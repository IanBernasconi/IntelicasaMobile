package com.example.intelicasamobile.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.MainUiState
import com.example.intelicasamobile.model.Device
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Preview(name = "DeviceInfo")
@Composable
fun DeviceInfoPreview() {
    DeviceInfo(device = MainUiState().devices[0])
}

@Composable
fun DeviceInfo(
    device: Device,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = device.deviceType.imageResourceId),
                contentDescription = "",
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
            )

            Text(
                text = stringResource(id = device.name), modifier = Modifier
                    .padding(
                        start = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
                    .align(Alignment.CenterVertically)
            )
        }
        when (device.meta.category.name) {
            "LAMP" -> LightDeviceInfo(device = device)
//            "AIR_CONDITIONER" -> ACDeviceInfo(device = device)
//            "OVEN" -> OvenDeviceInfo(device = device)
//            "VACUUM_CLEANER" -> VacuumDeviceInfo(device = device)
//            "DOOR" -> DoorDeviceInfo(device = device)
//            else -> DevicePower(device = device)
        }
    }
}

@Composable
fun LightDeviceInfo(
    device: Device,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localIntensity by remember { mutableStateOf(0) }
    var localColor by remember { mutableStateOf(Color.Black) }
    var showColorPicker by remember { mutableStateOf(false) }
    val colorController = rememberColorPickerController()

    Column(modifier = modifier) {
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

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.width(90.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Slider(
                        value = localIntensity.toFloat(),
                        onValueChange = { value ->
                            localIntensity = value.toInt()
                        },
                        steps = 1,
                        enabled = !(disabled || loading),
                        modifier = Modifier.fillMaxWidth()
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

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { showColorPicker = true },
                    modifier = Modifier
                        .size(40.dp),
                    //colors = ButtonDefaults.buttonColors(backgroundColor = localColor)
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
