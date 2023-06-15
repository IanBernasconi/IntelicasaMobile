package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.LightDevice
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun LightDeviceInfoPreview() {
    val device = LightDevice(deviceId = "1", deviceName = "Luz")
    LightDeviceInfo(
        device = device
    )
}

@Composable
fun LightDeviceInfo(
    modifier: Modifier = Modifier,
    device: LightDevice = viewModel(),
) {

    val uiState by device.state.collectAsState()

    var localIntensity by remember { mutableStateOf(uiState.brightness) }
    var localColor by remember { mutableStateOf(uiState.color) }
    val colorController = rememberColorPickerController()

    var updateTimeout by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    var isFirstColorSet = true

    LaunchedEffect(device) {
        colorController.setEnabled(!device.isLoading())
    }

    fun setColor(color: Color) {
        updateTimeout?.cancel()
        updateTimeout = null
        updateTimeout = scope.launch {
            delay(250) // wait for 250ms before sending the request
            if (!isFirstColorSet || color != Color.White) {
                device.setColor(color)
            }
            isFirstColorSet = false
        }
    }

    Row {
        Column(modifier = modifier.weight(1f)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                StateInfo(
                    isOn = uiState.isOn,
                    setIsOn = { device.setIsOn(it) },
                    modifier = modifier,
                    loading = device.isLoading()
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
                        text = stringResource(id = R.string.LI_brightness),
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
                            onValueChangeFinished = { device.setBrightness(localIntensity) },
                            valueRange = 0f..100f,
                            steps = 0,
                            enabled = !device.isLoading(),
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
                    Text(
                        text = stringResource(id = R.string.LI_color),
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
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = null,
                            tint = uiState.color,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .padding(5.dp),
                        )
                    }
                }
            }

            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HsvColorPicker(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(5.dp),
                        controller = colorController,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            localColor = colorEnvelope.color
                            setColor(localColor)
                        }
                    )
                }
            }
        }
        if (LocalConfiguration.current.orientation != Configuration.ORIENTATION_PORTRAIT) {
            HsvColorPicker(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .width(250.dp)
                    .padding(5.dp),
                controller = colorController,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    localColor = colorEnvelope.color
                    setColor(localColor)
                }
            )
        }
    }
}