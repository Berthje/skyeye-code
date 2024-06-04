package com.howest.skyeye.ui.home.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.howest.skyeye.ui.aircraft.SeeAllAircraftTypesDestination
import com.howest.skyeye.ui.airport.SeeAllAirportsDestination
import com.howest.skyeye.ui.camera.CameraDestination
import com.howest.skyeye.ui.home.HomeScreen
import com.howest.skyeye.ui.settings.SettingsDestination
import com.howest.skyeye.ui.settings.account.AccountDestination
import com.howest.skyeye.ui.user.UserViewModel
import com.howest.skyeye.ui.user.LoginDestination
import com.howest.skyeye.ui.user.RegisterDestination
import howest.nma.skyeye.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(userViewModel: UserViewModel, navigateTo: (route: String) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = listOf(
        //Triple(R.drawable.weather, "Check the weather", "weather"),
        Triple(R.drawable.camera, "Search airplanes with AR", CameraDestination.route),
        Triple(R.drawable.airplane, "See all aircraft types", SeeAllAircraftTypesDestination.route),
        Triple(R.drawable.runway, "See all airports", SeeAllAirportsDestination.route),
        Triple(R.drawable.settings, "Settings", SettingsDestination.route)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = { DrawerContent(userViewModel = userViewModel, drawerState = drawerState, scope = scope, items = items, navigateTo = navigateTo) },
        scrimColor = Color.Black.copy(alpha = 0.8f),
        content = { HomeScreen(userViewModel = userViewModel, drawerState = drawerState, scope = scope, navigateTo = navigateTo) }
    )
}

@Composable
fun DrawerContent(userViewModel: UserViewModel, drawerState: DrawerState, scope: CoroutineScope, items: List<Triple<Int, String, String>>, navigateTo: (route: String) -> Unit) {
    ModalDrawerSheet(
        drawerShape = RectangleShape,
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
    ) {
        DrawerHeader(userViewModel = userViewModel, drawerState = drawerState, scope = scope, navigateTo = navigateTo)
        DrawerItems(items = items, drawerState = drawerState, scope = scope, navigateTo = navigateTo)
    }
}

@Composable
fun DrawerHeader(userViewModel: UserViewModel, drawerState: DrawerState, scope: CoroutineScope, navigateTo: (route: String) -> Unit) {
    val userUiState by userViewModel.userUiState.collectAsState()

    Spacer(Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { scope.launch { drawerState.close() } }) {
            Icon(
                Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(30.dp)
            )
        }

        if (userUiState.isLoggedIn) {
            TextButton(onClick = {
                scope.launch {
                    drawerState.close()
                    navigateTo(AccountDestination.route)
                } }) {
                Text(text = "Account", fontSize = 22.sp)
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    scope.launch {
                        drawerState.close()
                        navigateTo(RegisterDestination.route)
                    } }) {
                    Text(text = "Register", fontSize = 22.sp)
                }
                Text(text = "or", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 22.sp)
                TextButton(onClick = {
                    scope.launch {
                        drawerState.close()
                        navigateTo(LoginDestination.route)
                    } }) {
                    Text(text = "Log in", fontSize = 22.sp)
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 25.dp, end = 25.dp, top = 5.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant
    )
    Spacer(Modifier.height(18.dp))
}

@Composable
fun DrawerItem(
    iconId: Int,
    label: String,
    destination: String,
    navigateTo: (route: String) -> Unit,
    onClick: () -> Unit,
    bottomPadding: Dp = 0.dp // Default padding is 0.dp
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                painterResource(iconId),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = label,
                modifier = Modifier.size(26.dp)
            )
        },
        label = {
            Text(
                label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        selected = false,
        onClick = {
            onClick()
            navigateTo(destination)
        },
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding)
            .then(
                if (bottomPadding > 0.dp) {
                    Modifier.padding(bottom = bottomPadding)
                } else {
                    Modifier
                }
            )
    )
}

@Composable
fun DrawerItems(items: List<Triple<Int, String, String>>, drawerState: DrawerState, scope: CoroutineScope, navigateTo: (route: String) -> Unit) {
    Column {
        // Top items
        items.filter { it.second != "Settings" }.forEach { item ->
            DrawerItem(
                iconId = item.first,
                label = item.second,
                destination = item.third,
                navigateTo = navigateTo,
                onClick = { scope.launch { drawerState.close() } }
            )
        }

        // Spacer to separate top and bottom items
        Spacer(modifier = Modifier.weight(1f))

        // "Settings" item at the bottom with bottom padding
        items.find { it.second == "Settings" }?.let { settingsItem ->
            DrawerItem(
                iconId = settingsItem.first,
                label = settingsItem.second,
                destination = settingsItem.third,
                navigateTo = navigateTo,
                onClick = { scope.launch { drawerState.close() } },
                bottomPadding = 16.dp // Adjust the padding as needed
            )
        }
    }
}
