package com.example.pixabayapp.features.picture

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.pixabayapp.features.PixabayRoute
import com.example.pixabayapp.features.picture.ui.PhotoScreen


//@Composable
//fun rememberPhotoFeatureState(
//    networkMonitor: NetworkMonitor,
//    coroutineScope: CoroutineScope = rememberCoroutineScope(),
//    navController: NavHostController = rememberNavController(),
//
//    ): PhotoAppState {
//
//    return remember(networkMonitor, coroutineScope, navController) {
//        PhotoAppState(
//            networkMonitor = networkMonitor,
//            coroutineScope = coroutineScope,
//            navController = navController
//        )
//    }
//}
//
//
//@Stable
//class PhotoAppState(
//    val navController: NavHostController,
//    coroutineScope: CoroutineScope,
//    networkMonitor: NetworkMonitor
//) {
//
//    val currentDestination: NavDestination?
//        @Composable get() = navController
//            .currentBackStackEntryAsState().value?.destination
//
//    val isOffline = networkMonitor.isOnline
//        .map(Boolean::not)
//        .stateIn(
//            scope = coroutineScope,
//            started = SharingStarted.WhileSubscribed(5_000),
//            initialValue = false,
//        )
//
//
//}

fun NavGraphBuilder.pictureScreen() {
    composable(PixabayRoute.PHOTO) {
        PhotoScreen()
    }

}