package com.example.intelicasamobile.ui.devices

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.intelicasamobile.R
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceTypes

@Preview(showBackground = true)
@Composable
fun DeviceCardPreview() {
    DeviceCard(device = Device(DeviceTypes.LAMP, R.string.lamp), modifier = Modifier.padding(16.dp))
}
@Composable
fun DeviceCard(
    device: Device,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(modifier = modifier.clickable { showDialog = true } ) {
        Box(
            modifier = Modifier
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
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(
                                id = R.dimen.padding_small
                            )
                        )
                )
            }
        }
        DeviceInfoModal(device = device, showDialog = showDialog, onDismiss = { showDialog = false })
    }
}
