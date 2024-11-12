package com.example.ualaapp.data.service

import com.example.ualaapp.data.models.City
import com.example.ualaapp.helpers.Constants.GET_CITIES
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Axel Sanchez
 */
interface ApiServiceCity {
    @GET(GET_CITIES)
    suspend fun getCities(): Response<List<City>?>
}