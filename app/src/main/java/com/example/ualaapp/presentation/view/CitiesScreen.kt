package com.example.ualaapp.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel
import com.example.ualaapp.data.models.City
import com.example.ualaapp.presentation.view.components.ErrorState
import com.example.ualaapp.presentation.view.components.ToolbarApp
import com.example.ualaapp.presentation.view.components.Loading
import com.example.ualaapp.presentation.view.components.ProductList
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

/**
 * @author Axel Sanchez
 */
@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel,
    navigateToMapScreen: (Long) -> Unit,
    navigateToFavScreen: () -> Unit
) {

    viewModel.getCities()
    val dataCities: DataCities by viewModel.getCitiesLiveData()
        .observeAsState(initial = DataCities())

    val cityToShow: MutableState<City?> = remember { mutableStateOf(null) }

    DisposableEffect(dataCities) {
        onDispose {
            viewModel.reset()
        }
    }

    Scaffold(
        topBar = {
            ToolbarApp(navigateToFavScreen)
        },
        content = { paddingValues ->

            val config = LocalConfiguration.current
            val portraitMode = remember { mutableIntStateOf(config.orientation) }

            if (portraitMode.intValue == Configuration.ORIENTATION_PORTRAIT) {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(paddingValues)
                        .background(Color.Black)
                        .fillMaxSize()
                ) {
                    val (emptyState, loading) = createRefs()

                    ErrorState(modifier = Modifier.constrainAs(emptyState) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, dataCities)

                    Loading(modifier = Modifier.constrainAs(loading) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, dataCities)

                    ProductList(
                        dataCities,
                        navigateToMapScreen,
                        Modifier.fillMaxSize()
                    )
                }
            } else {
                ConstraintLayout(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    val (emptyState, loading) = createRefs()

                    ErrorState(modifier = Modifier.constrainAs(emptyState) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, dataCities)

                    Loading(modifier = Modifier.constrainAs(loading) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, dataCities)

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        ProductList(
                            dataCities,
                            navigateToMapScreen = { id ->
                                if (portraitMode.intValue == Configuration.ORIENTATION_PORTRAIT) {
                                    navigateToMapScreen(id)
                                } else {
                                    cityToShow.value = dataCities.cities?.find { it.id == id }
                                }
                            },
                            Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(16.dp)
                        )

                        if (!dataCities.cities.isNullOrEmpty()) {
                            val cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(
                                    LatLng(
                                        cityToShow.value?.coordinates?.lat ?: 0.0,
                                        cityToShow.value?.coordinates?.lon ?: 0.0
                                    ), 0f
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .padding(16.dp)
                            ) {
                                GoogleMap(
                                    cameraPositionState = cameraPositionState,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                cityToShow.value?.coordinates?.lat
                                                    ?: dataCities.cities?.first()?.coordinates?.lat
                                                    ?: 0.0,
                                                cityToShow.value?.coordinates?.lon
                                                    ?: dataCities.cities?.first()?.coordinates?.lon
                                                    ?: 0.0
                                            )
                                        ),
                                        title = cityToShow.value?.name
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    )
}