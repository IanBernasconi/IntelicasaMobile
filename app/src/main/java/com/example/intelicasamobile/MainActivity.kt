package com.example.intelicasamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme


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
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))) {
            Image(painter = painterResource(id = device.deviceType.imageResourceId), contentDescription = "", modifier= Modifier.size(dimensionResource(id = R.dimen.image_size)))

            Text(text = stringResource(id = device.name), modifier= Modifier.padding(start = dimensionResource(
                id = R.dimen.padding_small))
            )
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
)

val routines = listOf(
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),
    Rutine(R.string.rutine1),

)

@Composable
fun RoutineCard(
    rutine: Rutine,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color.Black)
        ) {
            Row(
                modifier = modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = stringResource(id = rutine.name),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFFFFFFF),
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)))
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

        }
    }
}


@Composable
fun CategoryCard(title: Int) {
    Card() {
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.Center,

            ) {
                Text(
                    text = stringResource(id = title),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF000000),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomePreview(){
    IntelicasaMobileTheme {
        Scaffold {
                LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small) ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                contentPadding = it
            ) {
                item(span = {
                    GridItemSpan(maxLineSpan)
                }) {
                    CategoryCard(title = R.string.routines)
                }

                items(routines) {rutine->
                    RoutineCard(
                        rutine = rutine,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }
    }
}



