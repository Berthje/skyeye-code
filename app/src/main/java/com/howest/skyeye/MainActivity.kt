package com.howest.skyeye

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.howest.skyeye.ui.AppViewModelProvider
import com.howest.skyeye.ui.SkyEyeApp
import com.howest.skyeye.ui.theme.SkyEyeTheme
import com.howest.skyeye.ui.theme.ThemeViewModel
import com.howest.skyeye.workers.ReminderWorker
import java.util.concurrent.TimeUnit

class MainActivity() : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(2, TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "skyEyeReminder",
            ExistingWorkPolicy.REPLACE,
            reminderWorkRequest
        )

        setContent {
            val themeViewModel: ThemeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val mainUiState by themeViewModel.themeUiState.collectAsState()

            LaunchedEffect(mainUiState.isDarkMode) {
                val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
                uiModeManager.nightMode = if (mainUiState.isDarkMode) UiModeManager.MODE_NIGHT_YES else UiModeManager.MODE_NIGHT_NO
            }

            SkyEyeTheme(darkTheme = mainUiState.isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SkyEyeApp()
                }
            }
        }
    }
}
