package com.example.ualaapp.domain.repository

import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities

/**
 * @author Axel Sanchez
 */
interface CityRepository {
    suspend fun getCities(): DataCities
    suspend fun getCity(idCity: Long): City
    suspend fun getCitiesByName(name: String): DataCities
    suspend fun getLocalCities(): List<City>
    suspend fun getRemoteCities(): DataCities
    suspend fun updateCity(city: City)
}