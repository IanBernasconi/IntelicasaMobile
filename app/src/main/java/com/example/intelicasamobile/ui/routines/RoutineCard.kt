package com.example.intelicasamobile.ui.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.DevicesViewModel
import com.example.intelicasamobile.data.RoutinesViewModel
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.receiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RoutineCard(
    routine: Routine,
    modifier: Modifier = Modifier,
    devicesModel: DevicesViewModel = viewModel(),
) {

    val devicesState by devicesModel.devicesUiState.collectAsState()
    val routinesModel by remember { mutableStateOf(RoutinesViewModel.getInstance()) }
    val routinesState by routinesModel.routinesUiState.collectAsState()
    Card(
        modifier = Modifier
            .size(300.dp, 120.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation))
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(3f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Column(
                        modifier = modifier.weight(2f)
                    ) {
                        Text(
                            text = routine.name,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Column(
                        modifier = modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ){
                        IconButton(
                            onClick = {
                                routine.actions.forEach { action ->
                                    receiver.addCurrentDeviceId(action.deviceId)
                                    receiver.removeCurrentDeviceId(action.deviceId)
                                }
                                CoroutineScope(Dispatchers.Main).launch {
                                    val apiService = RetrofitClient.getApiService()
                                    apiService.executeRoutine(id = routine.id)
                                    routinesModel.showSnackBar()
                                    devicesModel.fetchDevices()
                                }
                            },
                            enabled = !routinesState.isLoading
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                contentDescription = stringResource(R.string.play),
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(50.dp),
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(2f),
                ) {
                    val devices =
                        routine.actions.map { action -> devicesState.devices.find { it.id == action.deviceId } }
                            .distinct()
                    devices.forEach {
                        if (it != null) {
                            Image(
                                painter = painterResource(id = it.deviceType.imageResourceId),
                                contentDescription = stringResource(R.string.device_type),
                                modifier = Modifier
                                    .padding(4.dp, bottom = 10.dp)
                                    .size(dimensionResource(R.dimen.image_size))
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineCardPreview() {
    RoutineCard(
        routine = Routine(
            id = "1",
            name = "Test Routine",
            actions = listOf(),
        )
    )
}


