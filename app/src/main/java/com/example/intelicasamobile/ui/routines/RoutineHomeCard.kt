package com.example.intelicasamobile.ui.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.MainUiState
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Composable
fun RoutineHomeCard(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    IntelicasaMobileTheme() {
        Card(modifier = modifier) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = routine.name),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayCircle,
                            contentDescription = "Play Circle",
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = false)
@Composable
fun RoutineListPreview() {
    IntelicasaMobileTheme {
        Scaffold {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
                contentPadding = it
            ) {
                items(MainUiState().routines) { routine ->
                    RoutineHomeCard(
                        routine = routine,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }
    }
}
