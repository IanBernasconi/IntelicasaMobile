package com.example.intelicasamobile.ui.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource.routines
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Composable
fun RoutineCard(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .size(300.dp, 120.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation))
    ) {

        Surface(
            color = MaterialTheme.colorScheme.primary
        ){
            Column() {

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ){
                    Text(
                        text = stringResource(id = routine.name),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    IconButton(
                        onClick = {/* TODO */ },
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(50.dp),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    routine.devices.forEach { device ->
                        Image(
                            painter = painterResource(id = device.deviceType.imageResourceId),
                            contentDescription = "Device Type",
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RoutineCardPreview() {
    IntelicasaMobileTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
        ) {
            items(routines) { routine ->
                RoutineCard(
                    routine = routine,
                    Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}



