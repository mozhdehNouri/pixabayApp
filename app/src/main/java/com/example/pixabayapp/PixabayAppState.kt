package com.example.pixabayapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.pixabayapp.core.networkconnection.NetworkMonitor
import com.example.pixabayapp.features.PixabayRoute
import com.example.pixabayapp.features.home.homeScreen
import com.example.pixabayapp.features.picture.pictureScreen
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
    modifier: Modifier = Modifier
) {
    val bottomNavItems = BottomNavItem.entries.toList()
    var selectedNavItem by remember {
        mutableStateOf(bottomNavItems.first())
    }
    PixabayAppTheme {
        Scaffold(modifier = modifier.fillMaxSize(),
            bottomBar = { BottomAppBar( ) {
                bottomNavItems.forEach {
                    NavigationBarItem(
                        selected = it == selectedNavItem,
                        label = {
                            Text(it.title)
                        },
                        onClick = {
                            it.route?.let { route ->
                                appState.navigateTo(route)
                            }
                            selectedNavItem = it
                        },
                        icon = {
                            Image(
                                painter = painterResource(id = it.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                            )
                        })
                }
            }
        }) { innerPadding ->
            NavHost(
                navController = appState.navHostController,
                startDestination = PixabayRoute.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {
                homeScreen()
                pictureScreen()
            }
        }
    }
}

enum class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: String? = null,
) {
    Home(
        title = "Home",
        icon = R.drawable.ic_home,
        route = PixabayRoute.HOME
    ),
    Photo(
        title = "Photo",
        icon = R.drawable.ic_pic,
        route = PixabayRoute.PHOTO
    ),
    Video(
        title = "Video",
        icon = R.drawable.ic_video,
        route = PixabayRoute.VIDEO
    )
}
