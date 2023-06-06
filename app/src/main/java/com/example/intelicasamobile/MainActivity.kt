package com.example.intelicasamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small) ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
            contentPadding = it
        ) {
            items(devices) {device->
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
){
    Card(modifier = modifier) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_small))) {
            Image(painter = painterResource(id = device.imageResourceId), contentDescription = "", modifier= Modifier.size(dimensionResource(id = R.dimen.image_size)))
            Text(text = stringResource(id = device.name) )
        }
    }
}

data class Device(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
)

val devices = listOf(
    Device(R.drawable.lightbulb, R.string.lamp),
    Device(R.drawable.airconditioner, R.string.lamp),
    Device(R.drawable.oven, R.string.lamp),
    Device(R.drawable.vacuumcleaner, R.string.lamp),
    Device(R.drawable.door, R.string.lamp),
    Device(R.drawable.lightbulb, R.string.lamp),
    Device(R.drawable.airconditioner, R.string.lamp),
    Device(R.drawable.oven, R.string.lamp),
    Device(R.drawable.vacuumcleaner, R.string.lamp),
    Device(R.drawable.door, R.string.lamp),
    Device(R.drawable.lightbulb, R.string.lamp),
    Device(R.drawable.airconditioner, R.string.lamp),
    Device(R.drawable.oven, R.string.lamp),
    Device(R.drawable.vacuumcleaner, R.string.lamp),
    Device(R.drawable.door, R.string.lamp),

)
