package com.example.intelicasamobile.ui.devices

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.DoorState
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Preview(showBackground = true)
@Composable
fun StateInfoPreview() {
    StateInfo(isOn = true, setIsOn = { })
}

@Composable
fun StateInfo(
    isOn: Boolean,
    setIsOn: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    IntelicasaMobileTheme() {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(0.5f)
                    .padding(start = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.device_state),
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { setIsOn(!isOn) },
                    enabled = !disabled && !loading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        painterResource(id = if (isOn) R.drawable.poweron else R.drawable.poweroff),
                        contentDescription = if (isOn) "Turn off" else "Turn on",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}