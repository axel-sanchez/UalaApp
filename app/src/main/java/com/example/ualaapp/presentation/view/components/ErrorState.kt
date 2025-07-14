package com.example.ualaapp.presentation.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.helpers.Constants

/**
 * @author Axel Sanchez
 */
@Composable
fun ErrorState(modifier: Modifier, dataCities: DataCities) {
    dataCities.cities?.let { products ->
        if (products.isEmpty()) {
            ErrorCard(Constants.ApiError.EMPTY_CITIES.error, modifier)
        }
    } ?: run {
        dataCities.apiError?.let {
            ErrorCard(it.error, modifier)
        }
    }
}