package com.example.intelicasamobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.intelicasamobile.R
import com.example.intelicasamobile.ui.theme.IntelicasaMobileTheme

@Composable
fun CategoryCard(title: Int) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
        ){
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .align(Alignment.Center),
                ){
                Text(
                    text = stringResource(id = title),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    IntelicasaMobileTheme {
        CategoryCard(title = R.string.routines)
    }
}