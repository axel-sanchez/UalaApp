package com.example.ualaapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.data.repository.FakeRepository
import com.example.ualaapp.domain.usecase.GetAllCitiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers
import org.junit.*

class CitiesViewModelTest{
    private val repo = FakeRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun should_update_livedata_with_cities(){
        val useCase = object : GetAllCitiesUseCase {

            override suspend fun call(): DataCities {
                return repo.getCities()
            }
        }

        val viewModel = CitiesViewModel(useCase)
        runBlocking {
            viewModel.getCities()
            delay(1000L)
            val result = viewModel.getCitiesLiveData().value
            result?.cities?.let { cities ->
                ViewMatchers.assertThat(cities, Matchers.contains(repo.city1, repo.city2, repo.city3, repo.city4, repo.city5, repo.city6, repo.city7))
            }?: kotlin.run { Assert.fail("El Live Data no pudo ser actualizado con su nuevo valor") }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}