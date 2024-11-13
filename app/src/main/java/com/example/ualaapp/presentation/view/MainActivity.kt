package com.example.ualaapp.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.ualaapp.core.MyApplication
import com.example.ualaapp.domain.usecase.*
import com.example.ualaapp.navigation.NavigationHost
import com.example.ualaapp.presentation.ui.theme.UalaAppTheme
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel
import com.example.ualaapp.presentation.viewmodel.FavViewModel
import com.example.ualaapp.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAllCitiesUseCase: GetAllCitiesUseCase

    @Inject
    lateinit var getFavCitiesUseCase: GetFavCitiesUseCase

    @Inject
    lateinit var updateCityUseCase: UpdateCityUseCase

    @Inject
    lateinit var getCityUseCase: GetCityUseCase

    private val citiesViewModel: CitiesViewModel by viewModels(
        factoryProducer = { CitiesViewModel.CitiesViewModelFactory(getAllCitiesUseCase) }
    )

    private val mapViewModel: MapViewModel by viewModels(
        factoryProducer = { MapViewModel.MapViewModelFactory(getCityUseCase, updateCityUseCase) }
    )

    private val favViewModel: FavViewModel by viewModels(
        factoryProducer = { FavViewModel.FavViewModelFactory(getFavCitiesUseCase) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).component.inject(this)
        setContent {
            UalaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationHost(citiesViewModel, mapViewModel, favViewModel)
                }
            }
        }
    }
}