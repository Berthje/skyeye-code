package com.howest.skyeye.ui.home.modals.filter

import androidx.lifecycle.ViewModel
import com.howest.skyeye.ui.home.modals.FilterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilterViewModel() : ViewModel() {
    private val _filterUiState = MutableStateFlow(
        FilterUiState(
            selectedAirlines = emptyList(),
            selectedAircraft = emptyList(),
            selectedAirports = emptyList(),
            minAltitude = 0,
            minSpeed = 0
        )
    )
    val filterUiState: StateFlow<FilterUiState> = _filterUiState.asStateFlow()

    fun setFilterItem(filterItem: FilterUiState) {
        _filterUiState.value = filterItem
    }

    fun updateSelectedAirline(airline: String, isSelected: Boolean) {
        val currentSelectedAirlines = _filterUiState.value.selectedAirlines.toMutableList()
        if (isSelected) {
            currentSelectedAirlines.add(airline)
        } else {
            currentSelectedAirlines.remove(airline)
        }
        _filterUiState.value = _filterUiState.value.copy(selectedAirlines = currentSelectedAirlines)
    }

    fun updateSelectedAircraft(aircraft: String, isSelected: Boolean) {
        val currentSelectedAircrafts = _filterUiState.value.selectedAircraft.toMutableList()
        if (isSelected) {
            currentSelectedAircrafts.add(aircraft)
        } else {
            currentSelectedAircrafts.remove(aircraft)
        }
        _filterUiState.value = _filterUiState.value.copy(selectedAircraft = currentSelectedAircrafts)
    }

    fun updateSelectedAirport(airport: String, isSelected: Boolean) {
        val currentSelectedAirports = _filterUiState.value.selectedAirports.toMutableList()
        if (isSelected) {
            currentSelectedAirports.add(airport)
        } else {
            currentSelectedAirports.remove(airport)
        }
        _filterUiState.value = _filterUiState.value.copy(selectedAirports = currentSelectedAirports)
    }

    fun updateMinAltitude(minAltitude: Int) {
        _filterUiState.value = _filterUiState.value.copy(minAltitude = minAltitude)
    }

    fun updateMinSpeed(minSpeed: Int) {
        _filterUiState.value = _filterUiState.value.copy(minSpeed = minSpeed)
    }
}