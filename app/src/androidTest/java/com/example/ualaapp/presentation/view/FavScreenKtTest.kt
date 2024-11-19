package com.example.ualaapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ualaapp.helpers.Constants
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testTitle() {
        composeTestRule.onNodeWithTag(Constants.TEST_ICON_FAV)
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithTag(Constants.TEST_FAV_CITIES_TITLE)
            .assertIsDisplayed()
            .assertTextEquals("Ciudades Favoritas")

        composeTestRule.onNodeWithTag(Constants.TEST_FAV_CITIES)
            .assertIsDisplayed()
            .onChildren()
            .assertCountEquals(2)

        composeTestRule.onNodeWithTag(Constants.TEST_FAV_CITIES)
            .assertIsDisplayed()
            .onChildren()
            .onLast()
            .assert(hasText("Sydney, AU"))

        composeTestRule.onNodeWithTag(Constants.TEST_FAV_CITIES)
            .assertIsDisplayed()
            .onChildren()
            .onFirst()
            .assert(hasText("Anaheim, US"))
    }
}