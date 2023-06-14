package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

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

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { setIsOn(!isOn) },
                    enabled = !loading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = CircleShape
                    ) {
                    Image(
                        painterResource(id = if (isOn) R.drawable.poweron else R.drawable.poweroff),
                        contentDescription = if (isOn) stringResource(id = R.string.turn_off) else stringResource(
                            id = R.string.turn_on
                        ),
                        modifier = modifier.size(35.dp)
                    )
                }
            }
        }
    }
}