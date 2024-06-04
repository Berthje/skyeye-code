package com.howest.skyeye.ui.home.modals

data class WeatherUiState(
    val selectedWeatherItem: String
)

data class MapTypeUiState(
    val selectedMapTypeItem: String
)

data class FilterUiState(
    val selectedAirlines: List<String>,
    val selectedAircraft: List<String>,
    val selectedAirports: List<String>,
    val minAltitude: Int,
    val minSpeed: Int
)