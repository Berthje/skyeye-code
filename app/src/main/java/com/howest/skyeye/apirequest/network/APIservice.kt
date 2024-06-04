package com.howest.skyeye.apirequest.network

import com.howest.skyeye.ui.AirportApiData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://airportdb.io/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface APIservice {
    @GET("airport/{ICAO}?apiToken=54d773e23798fabc4fdc09f0107596a4bfa1634e6d4315d3100433ee82b15f2e7a679c6710f3cdc08691428c4373a6ee")
    suspend fun getAirportData(@Path("ICAO") icao: String): AirportApiData
}

object skyEyeApi {
    val retrofitService: APIservice by lazy {
        retrofit.create(APIservice::class.java)
    }
}
