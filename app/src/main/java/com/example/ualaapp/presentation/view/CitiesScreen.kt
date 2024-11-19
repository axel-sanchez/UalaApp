package com.example.ualaapp.presentation.view

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.helpers.Constants
import com.example.ualaapp.presentation.viewmodel.CitiesViewModel
import com.example.ualaapp.R
import com.example.ualaapp.data.models.City
import com.example.ualaapp.helpers.Constants.TEST_APP_NAME
import com.example.ualaapp.helpers.Constants.TEST_CITY_LIST
import com.example.ualaapp.helpers.Constants.TEST_ICON_FAV
import com.example.ualaapp.helpers.Constants.TEST_SEARCHER
import com.example.ualaapp.helpers.filterListByName
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
            val portraitMode = remember { mutableStateOf(config.orientation) }

            if (portraitMode.value == Configuration.ORIENTATION_PORTRAIT) {
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

                    ProductList(dataCities, navigateToMapScreen, Modifier.fillMaxSize())
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
                                if (portraitMode.value == Configuration.ORIENTATION_PORTRAIT) {
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
                                    .fillMaxHeight()  // Ocupa todo el alto disponible
                                    .weight(1f)  // Ocupa la otra mitad de la pantalla
                                    .padding(16.dp)
                            ) {
                                // Mapa de la ciudad
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

@Composable
private fun Loading(modifier: Modifier, dataCities: DataCities) {
    if (dataCities.cities == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier, dataCities: DataCities) {
    dataCities.cities?.let { products ->
        if (products.isEmpty()) {
            ErrorCard(Constants.ApiError.EMPTY_CITIES.error, modifier)
        }
    } ?: run {
        dataCities.apiError?.let {
            ErrorCard(it.error, modifier)
        }
    }
}

@Composable
fun ProductList(
    dataCities: DataCities,
    navigateToMapScreen: (Long) -> Unit,
    modifier: Modifier
) {
    if (!dataCities.cities.isNullOrEmpty()) {

        var query by rememberSaveable { mutableStateOf("") }

        Column(modifier = modifier) {
            TextField(
                modifier = Modifier
                    .testTag(TEST_SEARCHER)
                    .fillMaxWidth()
                    .padding(8.dp),
                value = query,
                onValueChange = { cityName: String ->
                    query = cityName
                },
                textStyle = TextStyle(
                    color = Color.Black, fontSize = 20.sp
                ),
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                placeholder = { Text(text = stringResource(R.string.busqueda)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )

            LazyColumn(modifier = Modifier.fillMaxWidth().testTag(TEST_CITY_LIST)) {
                val filterCities = filterListByName(dataCities.cities, query)
                if (filterCities.isEmpty()) {
                    item {
                        ErrorCard(Constants.ApiError.EMPTY_CITIES.error, Modifier.fillMaxWidth())
                    }
                } else {
                    itemsIndexed(filterCities) { _, city ->
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navigateToMapScreen(city.id ?: 0)
                                }) {
                            val (name, coordinates, divider) = createRefs()
                            Text(
                                modifier = Modifier
                                    .constrainAs(name) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        width = Dimension.fillToConstraints
                                    }
                                    .padding(top = 20.dp),
                                textAlign = TextAlign.Center,
                                text = "${city.name}, ${city.country}",
                                fontSize = 25.sp,
                                softWrap = true
                            )
                            Text(
                                modifier = Modifier
                                    .constrainAs(coordinates) {
                                        top.linkTo(name.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        width = Dimension.fillToConstraints
                                    }
                                    .padding(top = 10.dp, bottom = 20.dp),
                                textAlign = TextAlign.Center,
                                text = "${city.coordinates?.lat}, ${city.coordinates?.lon}",
                                softWrap = true,
                                fontSize = 18.sp
                            )

                            Divider(
                                modifier = Modifier.constrainAs(divider) {
                                    top.linkTo(coordinates.bottom)
                                },
                                color = colorResource(id = R.color.separator_gray),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolbarApp(navigateToFavScreen: () -> Unit){
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                color = Color.White,
                modifier = Modifier.testTag(TEST_APP_NAME)
            )
        },
        backgroundColor = Color.Black,
        actions = {
            IconButton(
                modifier = Modifier.testTag(TEST_ICON_FAV),
                onClick = {
                navigateToFavScreen()
            }) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Favorites",
                    tint = Color.Blue
                )
            }
        }
    )
}