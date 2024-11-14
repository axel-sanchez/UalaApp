package com.example.ualaapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.ualaapp.data.source.local.CityLocalSource
import com.example.ualaapp.data.source.remote.CityRemoteSource
import com.example.ualaapp.domain.repository.CityRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mockito

internal class CityRepositoryImplTest{
    private val repo = FakeRepository()

    private val cityRemoteSource: CityRemoteSource =
        Mockito.mock(CityRemoteSource::class.java)
    private val cityLocalSource: CityLocalSource =
        Mockito.mock(CityLocalSource::class.java)
    private val cityRepository: CityRepository = CityRepositoryImpl(cityRemoteSource, cityLocalSource)

    @Test
    fun should_calls_to_getRemoteProducts_when_there_are_not_local_products(){
        runBlocking {
            val mutableListData = MutableLiveData(repo.getRemoteCities())
            BDDMockito.given(cityRemoteSource.getCities()).willReturn(mutableListData)
            BDDMockito.given(cityRepository.getLocalCities()).willReturn(listOf())
            cityRepository.getCities()
            Mockito.verify(cityRemoteSource).getCities()
        }
    }

    @Test
    fun should_not_calls_to_getRemoteProducts_when_there_are_local_products(){
        runBlocking {
            BDDMockito.given(cityRepository.getLocalCities()).willReturn(listOf(repo.city1))
            cityRepository.getCities()
            Mockito.verify(cityRemoteSource, BDDMockito.never()).getCities()
        }
    }
}