package com.weather.skycast.core.data.remote

import com.weather.skycast.home.data.remote.CitySearchDto
import com.weather.skycast.home.data.remote.WeatherSummaryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("data/2.5/forecast")
    suspend fun getHourlyForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") apiKey: String = API_KEY
    ): WeatherSummaryDto


    @GET("geo/1.0/direct")
    suspend fun searchCity(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String = API_KEY
    ): List<CitySearchDto>

    companion object {
        const val API_KEY = "6ecb54ef0325ba60a0f8d2c3fe82c71a"
    }
}