package com.example.intelicasamobile.ui.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.data.network.RetrofitClient
import com.example.intelicasamobile.model.Routine
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun RoutineHomeCardPreview() {
    IntelicasaMobileTheme() {
        RoutineHomeCard(
            routine = Routine(
                id = "1",
                name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ",
                actions = listOf(),
            )
        )
    }
}
@Composable
fun RoutineHomeCard(
    routine: Routine,
    modifier: Modifier = Modifier
) {
    IntelicasaMobileTheme {
        Card(
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_elevation)),
            modifier = modifier
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .weight(3f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = modifier.weight(2f),

                    ) {
                        Text(
                            text = routine.name,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
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
                                // TODO ask about scope
                                CoroutineScope(Dispatchers.Main).launch {
                                    val apiService = RetrofitClient.getApiService()
                                    apiService.executeRoutine(id = routine.id)
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayCircle,
                                contentDescription = "Play Circle",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }

                }

            }
        }
    }
}


