package com.example.ualaapp.navigation

/**
 * @author Axel Sanchez
 */
sealed class Destinations(
    var route: String
){
    object CitiesScreen: Destinations("citiesScreen")
}
