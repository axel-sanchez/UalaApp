package com.example.ualaapp.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.ualaapp.R
import com.example.ualaapp.data.models.City
import com.example.ualaapp.data.models.Coordinates
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.helpers.Constants
import com.example.ualaapp.helpers.Constants.TEST_CITY_LIST
import com.example.ualaapp.helpers.Constants.TEST_SEARCHER
import com.example.ualaapp.helpers.filterListByName

/**
 * @author Axel Sanchez
 */
@Composable
fun ProductList(
    dataCities: DataCities,
    navigateToMapScreen: (Long) -> Unit,
    modifier: Modifier
) {
    if (!dataCities.cities.isNullOrEmpty()) {

        var query by rememberSaveable { mutableStateOf("") }

        Column(modifier = modifier) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                TextField(
                    modifier = Modifier
                        .testTag(TEST_SEARCHER)
                        .fillMaxWidth()
                        .background(color = Color.Gray),
                    value = query,
                    onValueChange = { cityName: String ->
                        query = cityName
                    },
                    textStyle = TextStyle(
                        color = Color.White, fontSize = 20.sp
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
            }

            LazyColumn(modifier = Modifier.fillMaxWidth().testTag(TEST_CITY_LIST)) {
                val filterCities = filterListByName(dataCities.cities, query)
                if (filterCities.isEmpty()) {
                    item {
                        ErrorCard(Constants.ApiError.EMPTY_CITIES.error, Modifier.fillMaxWidth())
                    }
                } else {
                    itemsIndexed(filterCities) { _, city ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                            , backgroundColor = Color.DarkGray
                        ) {
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        navigateToMapScreen(city.id ?: 0)
                                    }) {
                                val (name, coordinates) = createRefs()
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
                                    color = Color.White,
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
                                    color = Color.White,
                                    text = "${city.coordinates?.lat}, ${city.coordinates?.lon}",
                                    softWrap = true,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview(){
    ProductList(
        DataCities(cities = listOf(
            City(id = 1, name = "Cordoba", country = "AR", coordinates = Coordinates(1,0.0,0.0), fav = false),
            City(2, "Cordoba", "AR", Coordinates(2,0.0,0.0), false),
            City(3, "Cordoba", "AR", Coordinates(3,0.0,0.0), false),
        )),
        {},
        Modifier
    )
}