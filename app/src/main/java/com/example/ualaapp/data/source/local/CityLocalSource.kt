package com.example.ualaapp.data.source.local

import android.util.Log
import com.example.ualaapp.data.room.CityDao
import com.example.ualaapp.data.models.City
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface CityLocalSource {
    suspend fun getAllCities(): List<City>
    suspend fun getFavCities(): List<City>
    suspend fun getCity(idCity: Long): City
    suspend fun insertCity(city: City)
    suspend fun updateCity(city: City)
    suspend fun insertCities(cities: List<City>)
}

@Singleton
class CityLocalSourceImpl @Inject constructor(private val database: CityDao):
    CityLocalSource {

    override suspend fun getAllCities(): List<City> {
        return database.getCities()
    }

    override suspend fun getFavCities(): List<City> {
        return database.getFavCities()
    }

    override suspend fun getCity(idCity: Long): City {
        return database.getCity(idCity)
    }

    override suspend fun insertCity(city: City) {
        database.insertCity(city)
    }

    override suspend fun insertCities(cities: List<City>) {
        database.insertCities(cities)
    }

    override suspend fun updateCity(city: City) {
        database.updateCity(city)
    }
}