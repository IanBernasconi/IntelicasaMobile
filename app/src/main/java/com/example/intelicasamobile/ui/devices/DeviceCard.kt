package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceTypes

@Composable
fun DeviceCard(
    device: Device,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Image(
                    painter = painterResource(id = device.deviceType.imageResourceId),
                    contentDescription = "",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
                )

                Text(
                    text = stringResource(id = device.name), modifier = Modifier.padding(
                        start = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    DeviceCard(device = Device(DeviceTypes.LAMP, R.string.lamp))
}

