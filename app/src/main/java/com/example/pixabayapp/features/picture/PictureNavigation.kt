package com.example.pixabayapp.features.picture

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixabayapp.features.PixabayRoute
import com.example.pixabayapp.features.picture.ui.PhotoScreen


fun NavGraphBuilder.pictureScreen() {
    composable(PixabayRoute.PHOTO) {
        PhotoScreen()
    }

}