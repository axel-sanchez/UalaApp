package com.example.ualaapp.data.repository

import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.repository.CityRepository

/**
 * @author Axel Sanchez
 */
class FakeRepository: CityRepository {

    val city1 = City(id = 1, name = "Anaheim", country = "US")
    val city2 = City(id = 2, name = "Sydney", country = "AU")
    val city3 = City(id = 3, name = "Alabama", country = "US")
    val city4 = City(id = 4, name = "Arizona", country = "US")
    val city5 = City(id = 5, name = "Albuquerque", country = "US")
    val city6 = City(id = 6, name = "Cordoba", country = "ES")
    val city7 = City(id = 7, name = "Cordoba", country = "AR")

    val citiesList = listOf(city1, city2, city3, city4, city5, city6, city7)

    override suspend fun getCities(): DataCities {
        return DataCities(cities = citiesList)
    }

    override suspend fun getFavCities(): DataCities {
        return DataCities(cities = listOf(city1, city2))
    }

    override suspend fun getCity(idCity: Long): City {
        return city1
    }

    override suspend fun getLocalCities(): List<City> {
        return listOf()
    }

    override suspend fun getRemoteCities(): DataCities {
        return DataCities(cities = citiesList)
    }

    override suspend fun updateCity(city: City) {}
}