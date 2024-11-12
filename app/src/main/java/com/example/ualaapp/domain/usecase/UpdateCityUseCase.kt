package com.example.ualaapp.domain.usecase

import com.example.ualaapp.data.models.City
import com.example.ualaapp.domain.repository.CityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
interface UpdateCityUseCase{
    suspend fun call(city: City)
}

@Singleton
class UpdateCityUseCaseImpl @Inject constructor(private val repository: CityRepository):
    UpdateCityUseCase {
    override suspend fun call(city: City){
        return repository.updateCity(city)
    }
}
