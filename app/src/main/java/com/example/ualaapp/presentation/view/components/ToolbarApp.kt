package com.example.ualaapp.presentation.view.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.ualaapp.R
import com.example.ualaapp.helpers.Constants.TEST_APP_NAME
import com.example.ualaapp.helpers.Constants.TEST_ICON_FAV

/**
 * @author Axel Sanchez
 */
@Composable
fun ToolbarApp(navigateToFavScreen: () -> Unit){
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                color = Color.White,
                modifier = Modifier.testTag(TEST_APP_NAME)
            )
        },
        backgroundColor = Color.Black,
        actions = {
            IconButton(
                modifier = Modifier.testTag(TEST_ICON_FAV),
                onClick = {
                    navigateToFavScreen()
                }) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Favorites",
                    tint = Color.Blue
                )
            }
        }
    )
}