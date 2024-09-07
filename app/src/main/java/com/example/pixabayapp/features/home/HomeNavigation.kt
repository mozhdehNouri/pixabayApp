package com.example.pixabayapp.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixabayapp.features.PixabayRoute
import kotlinx.coroutines.CoroutineScope

fun NavGraphBuilder.homeScreen(coroutineScope: CoroutineScope) {
    composable(PixabayRoute.HOME) {
        HomeScreen(coroutineScope)
    }
}