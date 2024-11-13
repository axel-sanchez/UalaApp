package com.example.ualaapp.domain.usecase

import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface GetCityUseCase{
    suspend fun call(idCity: Long): City
}

@Singleton
class GetCityUseCaseImpl @Inject constructor(private val repository: CityRepository):
    GetCityUseCase {
    override suspend fun call(idCity: Long): City{
        return repository.getCity(idCity)
    }
}