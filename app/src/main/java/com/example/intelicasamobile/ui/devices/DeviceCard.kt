package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
    Card(modifier = modifier.clickable { /*TODO show modal*/ } ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = device.deviceType.imageResourceId),
                    contentDescription = "",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
                )

                Text(
                    text = stringResource(id = device.name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.fillMaxWidth().padding(
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

