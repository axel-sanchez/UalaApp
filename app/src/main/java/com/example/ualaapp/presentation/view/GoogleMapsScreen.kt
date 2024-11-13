package com.example.ualaapp.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
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
fun MapScreen(idCity: Long, mapViewModel: MapViewModel) {

    mapViewModel.getCity(idCity)
    val city: City? by mapViewModel.getCityLiveData()
        .observeAsState(initial = null)

    if (city?.coordinates?.lat != null && city?.coordinates?.lon != null) {
        // Mapa con la ubicación de la ciudad
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(city?.coordinates?.lat?:0.0, city?.coordinates?.lon?:0.0), 10f)
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Mapa de la ciudad
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier.fillMaxSize()
            ) {
                Marker(
                    state = MarkerState(position = LatLng(city?.coordinates?.lat?:0.0, city?.coordinates?.lon?:0.0)),
                    title = city?.name
                )
            }
        }
    }
}