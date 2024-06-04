package com.howest.skyeye.test

import android.net.http.HttpException
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.howest.skyeye.apirequest.network.APIservice
import com.howest.skyeye.apirequest.ui.APIUiStateAirportApiData
import com.howest.skyeye.apirequest.ui.APIViewModel
import com.howest.skyeye.ui.AirportApiData
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class APIViewModelTest {
    private lateinit var apiViewModel: APIViewModel

    @Before
    fun setUp() {
        apiViewModel = APIViewModel()
    }

    @Test
    fun testGetAirportData() = runTest {
        apiViewModel.getAirportData("EBAW")

        val uiState = apiViewModel.apiUiState

        when (uiState) {
            is APIUiStateAirportApiData.Loading -> {
            }
            is APIUiStateAirportApiData.Success -> {
                Assert.assertNotNull(uiState.data)
            }
            is APIUiStateAirportApiData.Error -> {
                Assert.fail("API request failed")
            }
        }
    }

    @Test
    fun testLoadingState() = runTest {
        apiViewModel.getAirportData("EBAW")

        val uiState = apiViewModel.apiUiState

        Assert.assertTrue(uiState is APIUiStateAirportApiData.Loading)
    }
}