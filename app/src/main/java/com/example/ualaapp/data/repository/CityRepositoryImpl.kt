package com.example.ualaapp.data.repository

import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.data.source.local.CityLocalSource
import com.example.ualaapp.data.source.remote.CityRemoteSource
import com.example.ualaapp.domain.repository.CityRepository
import com.example.ualaapp.helpers.Constants.ApiError.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Singleton
class CityRepositoryImpl @Inject constructor(
    private val cityRemoteSource: CityRemoteSource,
    private val cityLocalSource: CityLocalSource
) : CityRepository {

    override suspend fun getCities(): DataCities {
        val localCities = getLocalCities()
        if (localCities.isNotEmpty()) {
            return DataCities(cities = localCities)
        }

        val remoteDataCities = getRemoteCities()

        if (!remoteDataCities.cities.isNullOrEmpty()) {
            runBlocking(Dispatchers.IO) {
                addCitiesInDB(remoteDataCities.cities)
            }
        }

        return remoteDataCities
    }

    override suspend fun getFavCities(): List<City> {
        return cityLocalSource.getFavCities()
    }

    override suspend fun getCity(idCity: Long): City {
        return cityLocalSource.getCity(idCity)
    }

    override suspend fun getLocalCities(): List<City> {
        return cityLocalSource.getAllCities()
    }

    override suspend fun getRemoteCities(): DataCities {
        return cityRemoteSource.getCities().value ?: DataCities(apiError = GENERIC)
    }

    override suspend fun updateCity(city: City) {
        cityLocalSource.updateCity(city)
    }

    private suspend fun addCitiesInDB(cities: List<City>) {
        cityLocalSource.insertCities(cities)
    }
}