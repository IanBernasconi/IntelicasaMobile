package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Preview(showBackground = true)
@Composable
fun DeviceInfoHeaderPreview() {
    DeviceInfoHeader(device = ACDevice(deviceId = "1", deviceName = "Aire" ))
}

@Composable
fun DeviceInfoHeader(
    device: Device,
    modifier: Modifier = Modifier
) {
    IntelicasaMobileTheme {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = device.deviceType.imageResourceId),
                contentDescription = stringResource(id = device.deviceType.nameResId),
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
            )

            Text(
                text = device.name, modifier = Modifier
                    .padding(
                        start = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
                    .align(Alignment.CenterVertically)
            )


            IconButton(onClick = {}, enabled = false) {
//                Icon(
//                    imageVector = Icons.Filled.Delete,
//                    contentDescription = stringResource(id = R.string.delete),
//                    modifier = Modifier.size(25.dp)
//                )
            }
        }
    }
}

