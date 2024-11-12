package com.example.ualaapp.domain.usecase

import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetCitiesByNameUseCase{
    suspend fun call(name: String): DataCities
}

@Singleton
class GetCitiesByNameUseCaseImpl @Inject constructor(private val repository: CityRepository):
    GetCitiesByNameUseCase {
    override suspend fun call(name: String): DataCities {
        return repository.getCitiesByName(name)
    }
}