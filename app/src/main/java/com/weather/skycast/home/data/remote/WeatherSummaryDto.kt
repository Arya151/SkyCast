package com.weather.skycast.home.data.remote

data class WeatherSummaryDto(
    val city: CityWeatherDto,
    val cod: String,
    val list: List<WeatherItemDto>,
    val message: Int
)

data class WeatherItemDto(
    val dt: Int,
    val main: MainDto,
    val weather: List<WeatherDetailDto>
)

data class WeatherDetailDto(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class MainDto(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
)

data class CityWeatherDto(
    val country: String,
    val id: Int,
    val name: String,
)
