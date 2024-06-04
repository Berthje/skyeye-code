package com.howest.skyeye.ui.home.modals.weather

import androidx.lifecycle.ViewModel
import com.howest.skyeye.ui.home.modals.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel() : ViewModel() {
    private val _weatherUiState = MutableStateFlow(WeatherUiState("No weather"))
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    fun setWeatherItem(weatherItem: String) {
        _weatherUiState.value = WeatherUiState(selectedWeatherItem = weatherItem)
    }
}