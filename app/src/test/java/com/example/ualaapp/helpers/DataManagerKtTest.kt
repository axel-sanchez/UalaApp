package com.example.ualaapp.helpers

import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.models.City
import org.hamcrest.Matchers
import org.junit.Test
internal class DataManagerKtTest{
    private val city1 = City(id = 1, name = "Anaheim", country = "US")
    private val city2 = City(id = 2, name = "Sydney", country = "AU")
    private val city3 = City(id = 3, name = "Alabama", country = "US")
    private val city4 = City(id = 4, name = "Arizona", country = "US")
    private val city5 = City(id = 5, name = "Albuquerque", country = "US")
    private val city6 = City(id = 6, name = "Cordoba", country = "ES")
    private val city7 = City(id = 7, name = "Cordoba", country = "AR")

    private val citiesList = listOf(city1, city2, city3, city4, city5, city6, city7)

    @Test
    fun should_return_ordered_list(){
        ViewMatchers.assertThat(
            sortAlphabetically(citiesList),
            Matchers.contains(city3, city5, city1, city4, city7, city6, city2)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_s(){
        ViewMatchers.assertThat(
            filterListByName(citiesList, "s"),
            Matchers.contains(city2)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_co(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(citiesList, "co")),
            Matchers.contains(city7, city6)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_a(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(citiesList, "a")),
            Matchers.contains(city3, city5, city1, city4)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_al(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(citiesList, "Al")),
            Matchers.contains(city3, city5)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_alb(){
        ViewMatchers.assertThat(
            filterListByName(citiesList, "Alb"),
            Matchers.contains(city5)
        )
    }
}