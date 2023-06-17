package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
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
    fun favoriteHandler(device: Device) {
        device.toggleNewFavorite(device.meta.favorite)
    }

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
                text = device.name,
                style = TextStyle(fontSize= 20.sp),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
                    .align(Alignment.CenterVertically)
            )
            IconButton(
                onClick = { favoriteHandler(device)},
                enabled = !device.isLoading()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.star),
                    tint = if(device.meta.favorite)Color.Yellow else Color.Gray,
                    modifier = Modifier.size(50.dp),
                )
            }
        }
    }


}


