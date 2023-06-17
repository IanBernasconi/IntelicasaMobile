package com.example.intelicasamobile.ui.devices

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.data.RoomsViewModel
import com.example.intelicasamobile.model.ACDevice
import com.example.intelicasamobile.model.Device
import com.example.intelicasamobile.model.DeviceType
import com.example.intelicasamobile.model.DoorDevice
import com.example.intelicasamobile.model.LightDevice
import com.example.intelicasamobile.model.OvenDevice
import com.example.intelicasamobile.model.VacuumDevice
import com.example.intelicasamobile.receiver

@Preview(name = "DeviceInfo")
@Composable
fun DeviceInfoPreview() {
    //DeviceInfo(device = MainUiState().devices[1])
}

@Preview
@Composable
fun DeviceInfoModalPreview() {
      DeviceInfoModal(device = Device(id="1", name = "test", deviceType= DeviceType.AIR_CONDITIONER ), showDialog = true, onDismiss = {})
}

@Composable
fun DeviceInfoModal(
    device: Device,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    roomsViewModel: RoomsViewModel = viewModel(),
) {

    LaunchedEffect(showDialog){
        if (showDialog)
            receiver.addCurrentDeviceId(device.id)
        else
            receiver.removeCurrentDeviceId(device.id)
    }


    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = modifier.background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
            ) {
                DeviceInfo(
                    device = device,
                    modifier = modifier,
                    roomsViewModel = roomsViewModel,
                )
            }
        }
    }
}

@Composable
fun DeviceInfo(
    device: Device,
    modifier: Modifier = Modifier,
    roomsViewModel: RoomsViewModel = viewModel(),
) {

    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Card(modifier = modifier.width(300.dp)) {
            Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                Info(device, roomsViewModel, modifier)
            }
        }
    }else{
        Card(modifier = modifier.width(800.dp)) {
            Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
                Info(device, roomsViewModel, modifier)
            }
        }
    }

}

@Composable
fun Info(
    device: Device,
    roomsViewModel: RoomsViewModel = viewModel(),
    modifier: Modifier
) {
    Column {
        DeviceInfoHeader(device = device)
        when (device) {
            is LightDevice -> LightDeviceInfo(
                device = device,
            )

            is ACDevice -> {
                ACDeviceInfo(
                    device = device,
                )
            }

            is VacuumDevice -> VacuumDeviceInfo(
                device = device,
                roomsViewModel = roomsViewModel
            )

            is OvenDevice -> OvenDeviceInfo(
                device = device,
            )

            is DoorDevice -> DoorDeviceInfo(
                device = device,
            )
        }
    }

}

