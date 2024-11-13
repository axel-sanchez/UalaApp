package com.example.ualaapp.helpers

import com.example.ualaapp.data.models.City

/**
 * @author Axel Sanchez
 */
fun sortAlphabetically(list: List<City>): List<City>{
    return list
        .sortedWith(compareBy({ it.name }, { it.country }))
}

fun filterListByName(cities: List<City>, name: String): List<City>{
    return cities.filter {
        it.name?.startsWith(name, ignoreCase = true) ?: false
    }
}