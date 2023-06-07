package com.example.intelicasamobile.ui.routines

import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.Datasource.routines
import com.example.intelicasamobile.data.MainUiState
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Composable
fun RoutineCard(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
        ){
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ){
                Text(
                    text = stringResource(id = routine.name),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
                Spacer(modifier = Modifier.size(150.dp))

            }
            Row() {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RoutineCardPreview() {
    IntelicasaMobileTheme {
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(routines) { routine ->
                RoutineCard(
                    routine = routine,
                    Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
                )
            }
        }
    }
}