package com.example.ualaapp.presentation.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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

/**
 * @author Axel Sanchez
 */

@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel
) {

    viewModel.getCities()
    val dataCities: DataCities by viewModel.getCitiesLiveData()
        .observeAsState(initial = DataCities())

    DisposableEffect(dataCities) {
        onDispose {
            viewModel.reset()
        }
    }

    ConstraintLayout(
        modifier = Modifier
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

        ProductList(dataCities, viewModel)
    }
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
fun ProductList(dataCities: DataCities, viewModel: CitiesViewModel) {
    val context = LocalContext.current
    if (!dataCities.cities.isNullOrEmpty()) {

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
                itemsIndexed(dataCities.cities.filter {
                    it.name?.contains(query, ignoreCase = true)?:false
                }) { index, city ->
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navigateToGoogleMaps(
                                    context,
                                    city.coordinates?.lat,
                                    city.coordinates?.lon
                                )
                            }) {
                        val (name, coordinates, divider, icon) = createRefs()
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

                        var isFav by remember { mutableStateOf(city.fav?:false) }

                        val imageResource = if (isFav) R.drawable.ic_fav
                        else R.drawable.ic_nofav

                        Image(
                            painter = painterResource(id = imageResource),
                            contentDescription = "Descripción de la imagen",
                            modifier = Modifier
                                .constrainAs(icon) {
                                    top.linkTo(name.top)
                                    bottom.linkTo(coordinates.bottom)
                                    end.linkTo(parent.end)
                                }
                                .padding(end = 20.dp)
                                .clickable {
                                    isFav = !isFav
                                    city.fav = isFav
                                    viewModel.updateCity(city)

                                }
                        )

                        if (index != dataCities.cities.size - 1) {
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

fun navigateToGoogleMaps(context: Context, latitude: Double?, longitude: Double?) {
    try {
        val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo abrir la ubicación en google maps", Toast.LENGTH_SHORT)
            .show()
    }
}