package com.example.ualaapp.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ualaapp.navigation.Destinations.CitiesScreen
import com.example.ualaapp.presentation.view.CitiesScreen
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun NavigationHost(citiesViewModel: CitiesViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CitiesScreen.route){
        composable(CitiesScreen.route){
            CitiesScreen(citiesViewModel)
        }
    }
}

@Composable
fun NavigateToGoogleMaps(context: Context, latitude: Double?, longitude: Double?) {
    // Construir la URI para abrir Google Maps
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps") // Especificamos la app de Google Maps

    // Asegurarnos de que hay una app de Google Maps instalada
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}