package com.howest.skyeye.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.home.bottombar.BottomBar
import com.howest.skyeye.ui.home.modals.maptype.MapTypeViewModel
import com.howest.skyeye.ui.home.topbar.TopBar
import com.howest.skyeye.ui.map.MapView
import com.howest.skyeye.ui.user.UserViewModel
import com.mapbox.mapboxsdk.camera.CameraPosition
import kotlinx.coroutines.CoroutineScope

object HomeDestination : NavigationDestination {
    override val route: String = "home"
    override val title: String = "Home"
}

@Composable
fun HomeScreen(userViewModel: UserViewModel, viewModel: MapTypeViewModel = viewModel(factory = AppViewModelProvider.Factory), drawerState: DrawerState, scope: CoroutineScope, navigateTo: (route: String) -> Unit) {
    val uiState by viewModel.mapTypeUiState.collectAsState()
    val cameraPositionState = remember { mutableStateOf<CameraPosition?>(null) }

    Scaffold(
        topBar = {
            TopBar(userViewModel = userViewModel, navigateTo = navigateTo, drawerState = drawerState, scope = scope)
        },
        bottomBar = {
            BottomBar(navigateTo)
        }
    ) { paddingValues ->
        MapView(
            modifier = Modifier.padding(paddingValues),
            latitude = 50.5,
            longitude = 4.47,
            context = LocalContext.current,
            showAirports = true,
            selectedMapTypeSetting = uiState.selectedMapTypeItem,
            cameraPositionState = cameraPositionState,
            navigateTo = navigateTo
        )
    }
}
