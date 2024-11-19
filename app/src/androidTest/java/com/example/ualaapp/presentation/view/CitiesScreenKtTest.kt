package com.example.ualaapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ualaapp.helpers.Constants.TEST_APP_NAME
import com.example.ualaapp.helpers.Constants.TEST_CITY_LIST
import com.example.ualaapp.helpers.Constants.TEST_SEARCHER
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CitiesScreenKtTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testAreCityDisplayed() {
        composeTestRule.onNodeWithTag(TEST_APP_NAME)
            .assertIsDisplayed()
            .assertTextEquals("UalaApp")
    }

    @Test
    fun testTagCount() {
        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(7)
    }

    @Test
    fun testCitiesNameText() {
        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildAt(1)
            .assert(hasText("Sydney, AU"))

        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .onLast()
            .assert(hasText("Cordoba, AR"))
    }

    @Test
    fun testFilterCitiesNameText(){
        composeTestRule.onNodeWithTag(TEST_SEARCHER)
            .assertIsDisplayed()
            .performTextInput("Al")

        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(2)

        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .onLast()
            .assert(hasText("Albuquerque, US"))

        composeTestRule.onNodeWithTag(TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .onFirst()
            .assert(hasText("Alabama, US"))
    }
}