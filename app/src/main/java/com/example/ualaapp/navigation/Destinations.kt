package com.example.ualaapp.navigation

import com.example.ualaapp.helpers.Constants.ID_CITY

/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object CitiesScreen: Destinations("citiesScreen")

    object MapScreen: Destinations("mapScreen/{$ID_CITY}"){
        fun createRoute(idCity: Long) = "mapScreen/$idCity"
    }

    object FavScreen: Destinations("favScreen")
}
