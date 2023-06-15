package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.LightDevice
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
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

    val colorController = rememberColorPickerController()

    val scrollState = rememberScrollState()

    LaunchedEffect(device) {
        colorController.setEnabled(!device.isLoading())
    }

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    if(orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = modifier) {
            LightState(device = device, isOn = uiState.isOn, modifier = modifier)
            LightBrightness(device = device, brightness = uiState.brightness, modifier = modifier)
            LightColorBox(color = uiState.color, modifier = modifier)
            LightColorPicker(device = device, color = uiState.color, colorPickerController = colorController, modifier = modifier)
        }
    } else {
        Row(
            modifier = modifier
                .verticalScroll(state = scrollState)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = modifier.fillMaxWidth(0.5f)
            ) {
                LightState(device = device, isOn = uiState.isOn, modifier = modifier)
                LightBrightness(device = device, brightness = uiState.brightness, modifier = modifier)
                LightColorBox(color = uiState.color, modifier = modifier)
            }
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                LightColorPicker(device = device, color = uiState.color, colorPickerController = colorController, modifier = modifier)
            }
        }
    }
}

@Composable
fun LightState(
    device: LightDevice = viewModel(),
    isOn: Boolean,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        StateInfo(
            isOn = isOn,
            setIsOn = { device.setIsOn(it) },
            modifier = modifier,
            loading = device.isLoading()
        )
    }
}

@Composable
fun LightBrightness(
    device: LightDevice = viewModel(),
    brightness: Int,
    modifier: Modifier,
) {
    var localIntensity by remember { mutableStateOf(brightness) }

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
}

@Composable
fun LightColorBox(
    color: Color,
    modifier: Modifier
) {
    var showColorPicker by remember { mutableStateOf(false) }

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
                    tint = color,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(5.dp)
                        .clickable { showColorPicker = true },
                )
            }
        }
    }
}

@Composable
fun LightColorPicker(
    device: LightDevice = viewModel(),
    color: Color,
    colorPickerController: ColorPickerController,
    modifier: Modifier
) {
    var localColor by remember { mutableStateOf(color) }
    var updateTimeout by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    var isFirstColorSet = true
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
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            HsvColorPicker(modifier = Modifier
                .width(150.dp)
                .height(150.dp),
                controller = colorPickerController,
                onColorChanged = { colorEnvelope: ColorEnvelope ->
                    localColor = colorEnvelope.color
                    setColor(localColor)
                }
            )
        }
    }
}