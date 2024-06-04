package com.howest.skyeye.test

import com.howest.skyeye.ui.home.modals.MapTypeUiState
import com.howest.skyeye.ui.home.modals.maptype.MapTypeViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapTypeModelTest {
    private lateinit var mapTypeViewModel: MapTypeViewModel

    @Before
    fun setup() {
        mapTypeViewModel = MapTypeViewModel()
    }

    @Test
    fun testSetMapTypeItemNormal() = runTest {
        val mapTypeItem = "normal"

        mapTypeViewModel.setMapTypeItem(mapTypeItem)

        assertEquals(MapTypeUiState(mapTypeItem), mapTypeViewModel.mapTypeUiState.value)
    }

    @Test
    fun testSetMapTypeItemTerrain() = runTest {
        val mapTypeItem = "terrain"

        mapTypeViewModel.setMapTypeItem(mapTypeItem)

        assertEquals(MapTypeUiState(mapTypeItem), mapTypeViewModel.mapTypeUiState.value)
    }

    @Test
    fun testSetMapTypeItemSatellite() = runTest {
        val mapTypeItem = "satellite"

        mapTypeViewModel.setMapTypeItem(mapTypeItem)

        assertEquals(MapTypeUiState(mapTypeItem), mapTypeViewModel.mapTypeUiState.value)
    }

    @Test
    fun testSetMapTypeItemDark() = runTest {
        val mapTypeItem = "dark"

        mapTypeViewModel.setMapTypeItem(mapTypeItem)

        assertEquals(MapTypeUiState(mapTypeItem), mapTypeViewModel.mapTypeUiState.value)
    }
}