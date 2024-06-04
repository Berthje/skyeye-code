package com.howest.skyeye.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howest.skyeye.data.userpreferences.UserPreferences
import com.howest.skyeye.data.userpreferences.UserPreferencesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(private val userPreferencesRepositoryInterface: UserPreferencesRepositoryInterface) : ViewModel() {
    private val _themeUiState = MutableStateFlow(ThemeUiState())
    val themeUiState: StateFlow<ThemeUiState> = _themeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferencesRepositoryInterface.userPreferences.collect { userPreferences ->
                _themeUiState.value = ThemeUiState(isDarkMode = userPreferences?.is_dark_mode ?: false)
            }
        }
    }

    suspend fun toggleDarkMode() {
        val current = _themeUiState.value
        _themeUiState.value = current.copy(isDarkMode = !current.isDarkMode)

        val id = userPreferencesRepositoryInterface.getLastInsertedId() ?: 0
        val newUserPreferences = UserPreferences(id = id, is_dark_mode = _themeUiState.value.isDarkMode)

        if (id == 0) {
            userPreferencesRepositoryInterface.insertUserPreferences(newUserPreferences)
        } else {
            userPreferencesRepositoryInterface.updateUserPreferences(newUserPreferences)
        }
    }
}