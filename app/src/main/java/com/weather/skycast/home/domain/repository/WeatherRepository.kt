package com.weather.skycast.home.domain.repository

import com.weather.skycast.core.data.remote.Resource
import com.weather.skycast.home.domain.model.CitySearch
import com.weather.skycast.home.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherData(latitude: Double, longitude: Double): Flow<Resource<Weather>>

    suspend fun getCities(city: String): Flow<Resource<List<CitySearch>>>

}