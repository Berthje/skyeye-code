package com.howest.skyeye.test

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.howest.skyeye.SkyEyeApplication
import com.howest.skyeye.data.AppDataContainer
import com.howest.skyeye.data.userpreferences.UserPreferences
import com.howest.skyeye.data.userpreferences.UserPreferencesRepositoryInterface
import com.howest.skyeye.ui.theme.ThemeViewModel;
import com.howest.skyeye.ui.user.UserViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith;
import java.util.logging.Logger

@RunWith(AndroidJUnit4::class)
class ThemeViewModelTest {
    private lateinit var themeViewModel: ThemeViewModel
    private lateinit var application: SkyEyeApplication
    private lateinit var testScope: TestScope
    private lateinit var userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface

    @Before
    fun setup() {
        application = ApplicationProvider.getApplicationContext()
        application.container = AppDataContainer(application)
        userPreferencesRepositoryInterface = application.container.userPreferencesRepositoryInterface
        themeViewModel = ThemeViewModel(application.container.userPreferencesRepositoryInterface)
        testScope = TestScope()
    }


    @Test
    fun testToggleDarkMode() = runTest {
        val currentUiState = themeViewModel.themeUiState.value

        themeViewModel.toggleDarkMode()

        val newUiState = themeViewModel.themeUiState.value

        assertEquals(!currentUiState.isDarkMode, newUiState.isDarkMode)
    }
}