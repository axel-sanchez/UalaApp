package com.example.ualaapp.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ualaapp.R
import com.example.ualaapp.data.models.DataCities
import com.example.ualaapp.helpers.Constants.TEST_FAV_CITIES_TITLE
import com.example.ualaapp.presentation.view.components.ErrorState
import com.example.ualaapp.presentation.view.components.Loading
import com.example.ualaapp.presentation.view.components.ProductList
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
    val favCities: DataCities by favViewModel.getFavCitiesLiveData()
        .observeAsState(initial = DataCities())

    DisposableEffect(favCities) {
        onDispose {
            favViewModel.reset()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.fav_cities),
                        Modifier.testTag(TEST_FAV_CITIES_TITLE),
                        color = Color.White,
                    )
                },  // Título del Toolbar
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
                    .background(Color.Black)
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

                ProductList(favCities, navigateToMapScreen, Modifier)
            }
        }
    )
}