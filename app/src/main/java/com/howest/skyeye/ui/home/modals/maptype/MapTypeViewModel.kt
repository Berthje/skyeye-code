package com.howest.skyeye.ui.home.modals.maptype

import androidx.lifecycle.ViewModel
import com.howest.skyeye.ui.home.modals.MapTypeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapTypeViewModel() : ViewModel() {
    private val _mapTypeUiState = MutableStateFlow(MapTypeUiState("normal"))
    val mapTypeUiState: StateFlow<MapTypeUiState> = _mapTypeUiState.asStateFlow()

    fun setMapTypeItem(mapTypeItem: String) {
        _mapTypeUiState.value = MapTypeUiState(selectedMapTypeItem = mapTypeItem)
    }
}