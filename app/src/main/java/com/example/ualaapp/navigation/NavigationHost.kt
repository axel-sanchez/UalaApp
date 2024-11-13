package com.example.ualaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ualaapp.helpers.Constants.ID_CITY
import com.example.ualaapp.navigation.Destinations.*
import com.example.ualaapp.presentation.view.CitiesScreen
import com.example.ualaapp.presentation.view.MapScreen
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel
import com.example.ualaapp.presentation.viewmodel.MapViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun NavigationHost(citiesViewModel: CitiesViewModel, mapViewModel: MapViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CitiesScreen.route){
        composable(CitiesScreen.route){
            CitiesScreen(citiesViewModel){ idCity ->
                navController.navigate(MapScreen.createRoute(idCity))
            }
        }

        composable(MapScreen.route){ navBackStackEntry ->
            val idCity = navBackStackEntry.arguments?.getString(ID_CITY)?:""
            MapScreen(
                idCity.toLong(),
                mapViewModel
            ){ navController.popBackStack() }
        }
    }
}