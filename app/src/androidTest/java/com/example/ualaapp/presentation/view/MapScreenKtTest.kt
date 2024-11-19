package com.example.ualaapp.presentation.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ualaapp.helpers.Constants
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MapScreenKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCityName() {

        composeTestRule.onNodeWithTag(Constants.TEST_CITY_LIST)
            .assertIsDisplayed()
            .onChildren()
            .onFirst()
            .performClick()

        composeTestRule.onNodeWithTag(Constants.TEST_TAG_CITY_NAME)
            .assertIsDisplayed()
            .assertTextEquals("Anaheim, US")
    }
}