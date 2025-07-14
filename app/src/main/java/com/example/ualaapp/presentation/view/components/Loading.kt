package com.example.ualaapp.presentation.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ualaapp.data.models.DataCities

/**
 * @author Axel Sanchez
 */
@Composable
fun Loading(modifier: Modifier, dataCities: DataCities) {
    if (dataCities.cities == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}