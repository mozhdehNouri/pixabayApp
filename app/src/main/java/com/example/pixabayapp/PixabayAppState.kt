package com.example.pixabayapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pixabayapp.core.networkconnection.NetworkMonitor
import com.example.pixabayapp.features.PixabayRoute
import com.example.pixabayapp.features.home.homeScreen
import com.example.pixabayapp.features.picture.pictureScreen
import com.example.pixabayapp.features.picture.ui.PhotoScreen
import com.example.pixabayapp.ui.theme.PixabayAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberPixabayAppState(
    networkMonitor: NetworkMonitor,
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): PixabayAppState {
    return remember(networkMonitor, coroutineScope, navHostController) {
        PixabayAppState(
            networkMonitor,
            coroutineScope = coroutineScope,
            navHostController = navHostController
        )
    }
}

@Stable
class PixabayAppState(
    networkMonitor: NetworkMonitor,
    val navHostController: NavHostController,
    coroutineScope: CoroutineScope
) {
    val isOffline = networkMonitor.isOnline.map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun navigateTo(destination: String) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun PixabayApp(
    appState: PixabayAppState,
//    navigateToVideo: () -> Unit,
//    navigateToPhoto: () -> Unit,
    exitApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    PixabayAppTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            NavHost(
                navController = appState.navHostController,
                startDestination = PixabayRoute.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                homeScreen(
                    navigateToPhoto = { appState.navigateTo(PixabayRoute.PHOTO) },
                    navigateToVideo = { appState.navigateTo(PixabayRoute.VIDEO) }
                )
                pictureScreen()
            }
        }
    }
}
