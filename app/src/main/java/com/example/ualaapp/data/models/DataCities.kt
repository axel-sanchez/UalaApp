package com.example.ualaapp.data.models

import com.example.ualaapp.helpers.Constants

data class DataCities(
    val cities: List<City>? = null,
    val apiError: Constants.ApiError? = null
)