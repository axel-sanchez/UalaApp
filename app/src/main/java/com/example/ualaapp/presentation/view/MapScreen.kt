package com.example.ualaapp.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.ualaapp.R
import com.example.ualaapp.data.models.City
import com.example.ualaapp.presentation.viewmodel.MapViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * @author Axel Sanchez
 */
@Composable
fun MapScreen(idCity: Long, mapViewModel: MapViewModel, onBackPressed: () -> Unit) {

    mapViewModel.getCity(idCity)
    val city: City by mapViewModel.getCityLiveData()
        .observeAsState(initial = City())

    DisposableEffect(city) {
        onDispose {
            mapViewModel.reset()
        }
    }

    if (city.coordinates?.lat != null && city.coordinates?.lon != null) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(city.name ?:"") },  // Título del Toolbar
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {

                        var isFav by remember { mutableStateOf(city.fav?:false) }

                        val imageResource = if (isFav) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder

                        IconButton(onClick = {
                            isFav = !isFav
                            city.fav = isFav
                            mapViewModel.updateCity(city)
                        }) {
                            if(city.fav == true) Icon(imageResource, contentDescription = "Favorites", tint = Color.Red)
                            else Icon(imageResource, contentDescription = "Favorites")
                        }
                    }
                )
            },
            content = { paddingValues ->
                // Mapa con la ubicación de la ciudad
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(city.coordinates?.lat?:0.0, city.coordinates?.lon?:0.0), 10f)
                }

                Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    // Mapa de la ciudad
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Marker(
                            state = MarkerState(position = LatLng(city.coordinates?.lat?:0.0, city.coordinates?.lon?:0.0)),
                            title = city.name
                        )
                    }
                }
            }
        )
    }
}