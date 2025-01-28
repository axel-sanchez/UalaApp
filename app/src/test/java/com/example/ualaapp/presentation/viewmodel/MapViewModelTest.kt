package com.example.ualaapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.repository.FakeRepository
import com.example.ualaapp.domain.usecase.GetCityUseCase
import com.example.ualaapp.domain.usecase.UpdateCityUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.Matchers
import org.junit.*
import org.mockito.Mockito

class MapViewModelTest{
    private val repo = FakeRepository()

    private val updateCityUseCase: UpdateCityUseCase =
        Mockito.mock(UpdateCityUseCase::class.java)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

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

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}