package com.example.pixabayapp.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixabayapp.features.PixabayRoute

fun NavGraphBuilder.homeScreen(
    navigateToPhoto: () -> Unit,
    navigateToVideo: () -> Unit
) {
    composable(PixabayRoute.HOME) {
        HomeScreen()
    }

}