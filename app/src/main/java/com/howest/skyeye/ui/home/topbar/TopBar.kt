package com.howest.skyeye.ui.home.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.howest.skyeye.ui.user.UserViewModel
import com.howest.skyeye.ui.settings.account.AccountDestination
import com.howest.skyeye.ui.user.LoginDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBar(userViewModel: UserViewModel, navigateTo: (route: String) -> Unit, drawerState: DrawerState, scope: CoroutineScope) {
    val userUiState by userViewModel.userUiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(Icons.Rounded.Menu, contentDescription = "menu", modifier = Modifier.size(32.dp))
        }
        IconButton(
            onClick = {
                if (userUiState.isLoggedIn) navigateTo(AccountDestination.route)
                else navigateTo(LoginDestination.route)
            }) {
            Icon(Icons.Rounded.AccountCircle, contentDescription = "avatar", modifier = Modifier.size(36.dp))
        }
    }
}