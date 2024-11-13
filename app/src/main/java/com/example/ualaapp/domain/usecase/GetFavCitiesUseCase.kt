package com.example.ualaapp.domain.usecase

import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetFavCitiesUseCase{
    suspend fun call(): List<City>
}

@Singleton
class GetFavCitiesUseCaseImpl @Inject constructor(private val repository: CityRepository):
    GetFavCitiesUseCase {
    override suspend fun call(): List<City> {
        return repository.getFavCities()
    }
}