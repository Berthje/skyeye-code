package com.howest.skyeye.test

import com.howest.skyeye.ui.home.modals.FilterUiState
import com.howest.skyeye.ui.home.modals.filter.FilterViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterViewModelTest {
    private lateinit var filterViewModel: FilterViewModel

    @Before
    fun setup() {
        filterViewModel = FilterViewModel()
    }


    @Test
    fun testSetFilterItem() = runTest {
        val filterItem = FilterUiState(
            selectedAirlines = listOf("Airline1"),
            selectedAircraft = listOf("Aircraft1"),
            selectedAirports = listOf("Airport1"),
            minAltitude = 1000,
            minSpeed = 200
        )

        filterViewModel.setFilterItem(filterItem)

        assertEquals(filterItem, filterViewModel.filterUiState.value)
    }

    @Test
    fun testUpdateSelectedAirline() = runTest {
        val airline = "Airline1"

        filterViewModel.updateSelectedAirline(airline, true)
        assertEquals(listOf(airline), filterViewModel.filterUiState.value.selectedAirlines)

        filterViewModel.updateSelectedAirline(airline, false)
        assertEquals(emptyList<String>(), filterViewModel.filterUiState.value.selectedAirlines)
    }

    @Test
    fun testUpdateSelectedAircraft() = runTest {
        val aircraft = "Aircraft1"

        filterViewModel.updateSelectedAircraft(aircraft, true)
        assertEquals(listOf(aircraft), filterViewModel.filterUiState.value.selectedAircraft)

        filterViewModel.updateSelectedAircraft(aircraft, false)
        assertEquals(emptyList<String>(), filterViewModel.filterUiState.value.selectedAircraft)
    }

    @Test
    fun testUpdateSelectedAirport() = runTest {
        val airport = "Airport1"

        filterViewModel.updateSelectedAirport(airport, true)
        assertEquals(listOf(airport), filterViewModel.filterUiState.value.selectedAirports)

        filterViewModel.updateSelectedAirport(airport, false)
        assertEquals(emptyList<String>(), filterViewModel.filterUiState.value.selectedAirports)
    }

    @Test
    fun testUpdateMinAltitude() = runTest {
        val minAltitude = 1000

        filterViewModel.updateMinAltitude(minAltitude)
        assertEquals(minAltitude, filterViewModel.filterUiState.value.minAltitude)
    }

    @Test
    fun testUpdateMinSpeed() = runTest {
        val minSpeed = 200

        filterViewModel.updateMinSpeed(minSpeed)
        assertEquals(minSpeed, filterViewModel.filterUiState.value.minSpeed)
    }
}