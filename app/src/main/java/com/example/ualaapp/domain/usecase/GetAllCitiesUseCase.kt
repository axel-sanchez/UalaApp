package com.example.ualaapp.domain.usecase

import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetAllCitiesUseCase{
    suspend fun call(): DataCities
}

@Singleton
class GetAllCitiesUseCaseImpl @Inject constructor(private val repository: CityRepository):
    GetAllCitiesUseCase {
    override suspend fun call(): DataCities {
        return repository.getCities()
    }
}