package com.example.intelicasamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import com.github.skydoves.colorpicker.compose.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntelicasaMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IntelicasaApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun IntelicasaApp() {
    Scaffold(
        topBar = {
            IntellicasaTopAppBar()
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            contentPadding = it
        ) {
            items(devices) { device ->
                DeviceCard(
                    device = device,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntellicasaTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_launcher_background),

                    // Content Description is not needed here - image is decorative, and setting a
                    // null content description allows accessibility services to skip this element
                    // during navigation.

                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun DeviceCard(
    device: Device,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
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

//@Preview(name = "DeviceInfo")
@Composable
fun DeviceInfoPreview() {
    DeviceInfo(device = devices[0])
}

@Composable
fun DeviceInfo(
    device: Device,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = device.deviceType.imageResourceId),
                contentDescription = "",
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_size))
            )

            Text(
                text = stringResource(id = device.name), modifier = Modifier
                    .padding(
                        start = dimensionResource(
                            id = R.dimen.padding_small
                        )
                    )
                    .align(Alignment.CenterVertically)
            )
        }
        when (device.meta.category.name) {
            "LAMP" -> LightDeviceInfo(device = device)
//            "AIR_CONDITIONER" -> ACDeviceInfo(device = device)
//            "OVEN" -> OvenDeviceInfo(device = device)
//            "VACUUM_CLEANER" -> VacuumDeviceInfo(device = device)
//            "DOOR" -> DoorDeviceInfo(device = device)
//            else -> DevicePower(device = device)
        }
    }
}

@Composable
fun LightDeviceInfo(
    device: Device,
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    loading: Boolean = false,
) {
    var localIntensity by remember { mutableStateOf(0) }
    var localColor by remember { mutableStateOf(Color.Black) }
    var showColorPicker by remember { mutableStateOf(false) }
    val colorController = rememberColorPickerController()

    Column(modifier = modifier) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                Text(text = "Intensidad", style = TextStyle(fontSize = 16.sp))
            }

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    modifier = Modifier.width(90.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Slider(
                        value = localIntensity.toFloat(),
                        onValueChange = { value ->
                            localIntensity = value.toInt()
                        },
                        steps = 1,
                        enabled = !(disabled || loading),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(0.dp, dimensionResource(id = R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.Start) {
                Text(text = "Color", style = TextStyle(fontSize = 16.sp))
            }

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { showColorPicker = true },
                    modifier = Modifier
                        .size(40.dp),
                    //colors = ButtonDefaults.buttonColors(backgroundColor = localColor)
                ) {}

                if (showColorPicker) {
                    AlertDialog(
                        onDismissRequest = { showColorPicker = false },
                        confirmButton = {
                            HsvColorPicker(
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(250.dp)
                                    .padding(5.dp),
                                controller = colorController,
                                onColorChanged = { colorEnvelope: ColorEnvelope ->
                                    val color: Color = colorEnvelope.color
                                    val hexCode: String = colorEnvelope.hexCode
                                    localColor = color
                                    showColorPicker = false
                                }
                            )
                        },
                        modifier = Modifier.padding(0.dp)
                    )

                }
            }
        }
    }
}

data class Device(
    val deviceType: DeviceTypes,
    @StringRes val name: Int,
    val meta: Meta = Meta()
)

data class Meta(
    val category: DeviceTypes = DeviceTypes.LAMP,
)

enum class DeviceTypes(
    @DrawableRes val imageResourceId: Int
) {
    LAMP(R.drawable.lightbulb),
    AIR_CONDITIONER(R.drawable.airconditioner),
    OVEN(R.drawable.oven),
    VACUUM_CLEANER(R.drawable.vacuumcleaner),
    DOOR(R.drawable.door),
}

val devices = listOf(
    Device(deviceType = DeviceTypes.LAMP, R.string.lamp),
    Device(deviceType = DeviceTypes.AIR_CONDITIONER, R.string.air_conditioner),
    Device(deviceType = DeviceTypes.OVEN, R.string.oven),
    Device(deviceType = DeviceTypes.VACUUM_CLEANER, R.string.vacuum_cleaner),
    Device(deviceType = DeviceTypes.DOOR, R.string.door),
    Device(deviceType = DeviceTypes.LAMP, R.string.lamp),
    Device(deviceType = DeviceTypes.AIR_CONDITIONER, R.string.air_conditioner),
    Device(deviceType = DeviceTypes.OVEN, R.string.oven),
    Device(deviceType = DeviceTypes.VACUUM_CLEANER, R.string.vacuum_cleaner),
    Device(deviceType = DeviceTypes.DOOR, R.string.door),
)

data class Rutine(
    @StringRes val name: Int,
    @DrawableRes val imageResourceId: Int = R.drawable.play
)

val rutines = listOf(
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),

)

@Composable
fun RutineCard(
    rutine: Rutine,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = stringResource(id = rutine.name),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
            Image(
                painter = painterResource(rutine.imageResourceId),
                contentDescription = null,
                modifier = modifier
                    .align(Alignment.CenterEnd)
                    .size(dimensionResource(id = R.dimen.image_size))
                    .padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RutineListPreview(){
    IntelicasaMobileTheme {
        Scaffold {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small) ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                contentPadding = it
            ) {
                items(rutines) {rutine->
                    RutineCard(
                        rutine = rutine,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }
    }
}



