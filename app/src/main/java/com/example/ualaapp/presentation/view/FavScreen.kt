package com.example.ualaapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.ualaapp.R
import com.example.ualaapp.data.models.City
import com.example.ualaapp.helpers.Constants
import com.example.ualaapp.presentation.viewmodel.FavViewModel

/**
 * @author Axel Sanchez
 */
@Composable
fun FavScreen(
    favViewModel: FavViewModel,
    onBackPressed: () -> Unit,
    navigateToMapScreen: (Long) -> Unit
) {
    favViewModel.getFavCities()
    val favCities: List<City>? by favViewModel.getFavCitiesLiveData()
        .observeAsState(initial = null)

    DisposableEffect(favCities) {
        onDispose {
            favViewModel.reset()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.fav_cities), color = Color.White) },  // Título del Toolbar
                backgroundColor = Color.Black,
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
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
                }, favCities)

                Loading(modifier = Modifier.constrainAs(loading) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, favCities)

                ProductList(favCities, navigateToMapScreen)
            }
        }
    )
}

@Composable
private fun Loading(modifier: Modifier, favCities: List<City>?) {
    if (favCities == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier, favCities: List<City>?) {
    if (favCities?.isEmpty() == true) {
        ErrorCard(Constants.ApiError.EMPTY_CITIES.error, modifier)
    }
}

@Composable
fun ProductList(
    favCities: List<City>?,
    navigateToMapScreen: (Long) -> Unit
) {
    if (!favCities.isNullOrEmpty()) {

        var query by rememberSaveable { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                modifier = Modifier
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

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(favCities) { index, city ->
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

                        if (index != favCities.size - 1) {
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