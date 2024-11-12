package com.example.ualaapp.presentation.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Axel Sanchez
 */
@Composable
fun ErrorCard(message: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
    ) {
        Text(
            text = message,
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            softWrap = true,
            style = TextStyle(fontSize = 18.sp)
        )
    }
}