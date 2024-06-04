package com.howest.skyeye.ui.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.NavigationDestination
import com.howest.skyeye.ui.settings.SettingsTopBar
import com.howest.skyeye.ui.theme.ThemeViewModel
import kotlinx.coroutines.launch

object AppearanceDestination : NavigationDestination {
    override val route: String = "appearance"
    override val title: String = "Appearance"
}

@Composable
fun AppearanceSettingsScreen(
    navigateBack: () -> Unit,
    viewModel: ThemeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val mainUiState by viewModel.themeUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SettingsTopBar(navigateBack, "Appearance settings")
        AppearanceSettingsItems(
            isDarkMode = mainUiState.isDarkMode,
            onDarkModeChange = {
                coroutineScope.launch {
                    viewModel.toggleDarkMode()
                }
            }
        )
    }
}

@Composable
fun AppearanceSettingsItems(isDarkMode: Boolean, onDarkModeChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DarkModeSwitch(
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange
            )
        }
    }
}

@Composable
fun DarkModeSwitch(isDarkMode: Boolean, onDarkModeChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "Darkmode",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)

            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isChecked ->
                    onDarkModeChange(isChecked)
                },
                modifier = Modifier
                    .size(30.dp)
                    .padding(top = 5.dp)
            )
        }
    }
}