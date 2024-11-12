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
import com.example.ualaapp.domain.usecase.GetAllCitiesUseCase
import com.example.ualaapp.domain.usecase.GetCitiesByNameUseCase
import com.example.ualaapp.domain.usecase.UpdateCityUseCase
import com.example.ualaapp.navigation.NavigationHost
import com.example.ualaapp.presentation.ui.theme.UalaAppTheme
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getAllCitiesUseCase: GetAllCitiesUseCase

    @Inject
    lateinit var getCitiesByNameUseCase: GetCitiesByNameUseCase

    @Inject
    lateinit var updateCityUseCase: UpdateCityUseCase

    private val citiesViewModel: CitiesViewModel by viewModels(
        factoryProducer = { CitiesViewModel.CitiesViewModelFactory(getAllCitiesUseCase, getCitiesByNameUseCase, updateCityUseCase) }
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
                    NavigationHost(citiesViewModel)
                }
            }
        }
    }
}