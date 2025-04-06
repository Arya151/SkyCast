package com.weather.skycast.home.domain.model

data class Weather(
    val city: String,
    val temp: Int,
    val feelsLike: Int,
    val pressure: Int,
    val seaLevel: Int,
    val forecast: List<ForecastItem>
)
