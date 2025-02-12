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
import androidx.compose.ui.platform.testTag
import com.example.ualaapp.data.models.City
import com.example.ualaapp.helpers.Constants.TEST_TAG_CITY_NAME
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

    Map(city, mapViewModel, onBackPressed)

    DisposableEffect(city) {
        onDispose {
            mapViewModel.reset()
        }
    }
}

@Composable
fun Map(city: City, mapViewModel: MapViewModel, onBackPressed: () -> Unit){
    if (city.coordinates?.lat != null && city.coordinates.lon != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(city.name ?:"", color = Color.White,
                        modifier = Modifier.testTag(TEST_TAG_CITY_NAME)) },  // Título del Toolbar
                    backgroundColor = Color.Black,
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
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
                            else Icon(imageResource, contentDescription = "Favorites", tint = Color.White)
                        }
                    }
                )
            },
            content = { paddingValues ->
                // Mapa con la ubicación de la ciudad
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(city.coordinates.lat, city.coordinates.lon), 10f)
                }

                Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    // Mapa de la ciudad
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Marker(
                            state = MarkerState(position = LatLng(city.coordinates.lat, city.coordinates.lon)),
                            title = city.name
                        )
                    }
                }
            }
        )
    }
}