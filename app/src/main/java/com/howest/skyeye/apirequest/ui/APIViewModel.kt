package com.howest.skyeye.apirequest.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.howest.skyeye.apirequest.network.skyEyeApi
import com.howest.skyeye.ui.AirportApiData
import com.howest.skyeye.ui.AirportData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class APIUiState {
    object Loading : APIUiState()
    data class Success(val data: AirportData) : APIUiState()
    object Error : APIUiState()
}

sealed class APIUiStateAirportData {
    object Loading : APIUiStateAirportData()
    data class Success(val data: AirportData) : APIUiStateAirportData()
    object Error : APIUiStateAirportData()
}

sealed class APIUiStateAirportApiData {
    object Loading : APIUiStateAirportApiData()
    data class Success(val data: AirportApiData) : APIUiStateAirportApiData()
    object Error : APIUiStateAirportApiData()
}

class APIViewModel : ViewModel() {
    var apiUiState: APIUiStateAirportApiData by mutableStateOf(APIUiStateAirportApiData.Loading)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getAirportData(icao: String) {
        viewModelScope.launch {
            apiUiState = APIUiStateAirportApiData.Loading
            apiUiState = try {
                val result = skyEyeApi.retrofitService.getAirportData(icao)
                APIUiStateAirportApiData.Success(result)
            } catch (e: IOException) {
                Log.e("ERROR", "${e.message}");
                APIUiStateAirportApiData.Error
            } catch (e: HttpException) {
                Log.e("HTTP ERROR", "${e.message}");
                APIUiStateAirportApiData.Error
            }
        }
    }
}