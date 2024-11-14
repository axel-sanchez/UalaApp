package com.example.ualaapp.helpers

import androidx.test.espresso.matcher.ViewMatchers
import com.example.ualaapp.data.repository.FakeRepository
import org.hamcrest.Matchers
import org.junit.Test
class DataManagerKtTest{

    private val repo = FakeRepository()

    @Test
    fun should_return_ordered_list(){
        ViewMatchers.assertThat(
            sortAlphabetically(repo.citiesList),
            Matchers.contains(repo.city3, repo.city5, repo.city1, repo.city4, repo.city7, repo.city6, repo.city2)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_s(){
        ViewMatchers.assertThat(
            filterListByName(repo.citiesList, "s"),
            Matchers.contains(repo.city2)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_co(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(repo.citiesList, "co")),
            Matchers.contains(repo.city7, repo.city6)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_a(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(repo.citiesList, "a")),
            Matchers.contains(repo.city3, repo.city5, repo.city1, repo.city4)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_al(){
        ViewMatchers.assertThat(
            sortAlphabetically(filterListByName(repo.citiesList, "Al")),
            Matchers.contains(repo.city3, repo.city5)
        )
    }

    @Test
    fun should_return_filtered_list_when_type_alb(){
        ViewMatchers.assertThat(
            filterListByName(repo.citiesList, "Alb"),
            Matchers.contains(repo.city5)
        )
    }
}