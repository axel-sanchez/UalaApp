package com.example.ualaapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.repository.FakeRepository
import com.example.ualaapp.domain.usecase.GetFavCitiesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class FavViewModelTest{
    private val repo = FakeRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun should_update_livedata_with_fav_cities(){
        val useCase = object : GetFavCitiesUseCase {

            override suspend fun call(): List<City> {
                return repo.getFavCities()
            }
        }

        val viewModel = FavViewModel(useCase)
        runBlocking {
            viewModel.getFavCities()
            delay(1000L)
            val result = viewModel.getFavCitiesLiveData().value
            result?.let { cities ->
                ViewMatchers.assertThat(cities, Matchers.contains(repo.city1, repo.city2))
            }?: kotlin.run { Assert.fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }
}