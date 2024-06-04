package com.howest.skyeye.ui.settings.account

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.home.HomeDestination
import com.howest.skyeye.ui.settings.SettingsTopBar
import com.howest.skyeye.ui.user.UserUiState
import com.howest.skyeye.ui.user.UserViewModel

object AccountDestination : NavigationDestination {
    override val route: String = "account"
    override val title: String = "Account"
}

@Composable
fun AccountSettingsScreen(userViewModel: UserViewModel, navigateBack: () -> Unit, navigateTo: (route: String) -> Unit) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navigateBack, "Account settings")
            AccountPage(userViewModel, navigateTo)
        }
    }
}

@Composable
fun AccountPage(userViewModel: UserViewModel, navigateTo: (route: String) -> Unit) {
    val focusManager = LocalFocusManager.current
    val userUiState by userViewModel.userUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp, horizontal = 50.dp),
    ) {
        ProfilePictureSection()
        AccountDetails(userUiState, focusManager)
        LogoutButton(userViewModel, navigateTo)
    }
}

@Composable
fun ProfilePictureSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = "Profile picture",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(200.dp)
        )
        IconButton(
            onClick = { /*TODO: Handle profile picture change*/ },
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.BottomCenter)
                .offset(x = (50).dp, y = (-15).dp)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge
                )
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Change profile picture",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AccountDetails(userUiState: UserUiState, focusManager: FocusManager) {
    AccountTextField(
        label = "Email",
        value = userUiState.email,
        focusManager = focusManager,
    )
}

@Composable
fun AccountTextField(label: String, value: String, focusManager: FocusManager) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        readOnly = true,
        shape = MaterialTheme.shapes.extraLarge,
        onValueChange = {},
        modifier = Modifier
            .padding(bottom = 20.dp, top = 40.dp)
            .fillMaxWidth()
            .onFocusChanged { if (it.isFocused) focusManager.clearFocus() }
    )
}

@Composable
fun LogoutButton(userViewModel: UserViewModel, navigateTo: (route: String) -> Unit) {
    TextButton(
        onClick = {
                    userViewModel.logout()
                    navigateTo(HomeDestination.route)
                  },
        modifier = Modifier
            .padding(top = 250.dp)
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .border(2.dp, MaterialTheme.colorScheme.onError, MaterialTheme.shapes.extraLarge)
            .width(200.dp)
    ) {
        Text(
            text = "Log out",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}