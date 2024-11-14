package com.example.ualaapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.repository.FakeRepository
import com.example.ualaapp.domain.usecase.GetCityUseCase
import com.example.ualaapp.domain.usecase.UpdateCityUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MapViewModelTest{
    private val repo = FakeRepository()

    private val updateCityUseCase: UpdateCityUseCase =
        Mockito.mock(UpdateCityUseCase::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_city(){
        val getCityUseCase = object : GetCityUseCase {

            override suspend fun call(idCity: Long): City {
                return repo.getCity(idCity)
            }
        }

        val viewModel = MapViewModel(getCityUseCase, updateCityUseCase)
        runBlocking {
            viewModel.getCity(1)
            delay(1000L)
            val result = viewModel.getCityLiveData().value
            result?.let { city ->
                ViewMatchers.assertThat(city, Matchers.equalTo(repo.city1))
            }?: kotlin.run { Assert.fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}