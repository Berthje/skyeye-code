package com.howest.skyeye.ui.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.user.UserViewModel
import com.howest.skyeye.ui.NavigationDestination

object SettingsDestination : NavigationDestination {
    override val route: String = "settings"
    override val title: String = "Settings"
}

@Composable
fun SettingsScreen(userViewModel: UserViewModel, navigateTo: (route: String) -> Unit, navigateBack: () -> Unit) {
    var isBackgroundLoaded by remember { mutableStateOf(false) }
    val userUiState by userViewModel.userUiState.collectAsState()

    LaunchedEffect(Unit) {
        isBackgroundLoaded = true
    }

    if (isBackgroundLoaded) {
        val items = listOf(
            if (userUiState.isLoggedIn) Triple("Account", "account",Icons.Rounded.AccountCircle)
            else Triple("Login or Sign up", "login" ,Icons.Rounded.AccountCircle),
            Triple("Appearance", "appearance",Icons.Rounded.Star),
            Triple("Support", "support",Icons.Rounded.Build),
            Triple("About", "about",Icons.Rounded.Info)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SettingsTopBar(navigateBack)
            SettingsItems(navigateTo, items)
        }
    }
}

@Composable
fun SettingsTopBar(navigateBack: () -> Unit, title: String = "Settings") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateBack) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun SettingsItems(navigateTo: (route: String) -> Unit, items: List<Triple<String, String, ImageVector>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
    ) {
        items.forEachIndexed { index, (item, label, icon) ->
            SettingsItem(navigateTo,item, label)
            if (index < items.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
fun SettingsItem(navigateTo: (route: String) -> Unit, item: String, label: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navigateTo(label)
            })
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = item,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "End Icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 5.dp)
            )
        }
    }
}
