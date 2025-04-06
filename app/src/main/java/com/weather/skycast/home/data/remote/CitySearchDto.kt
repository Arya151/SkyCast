package com.weather.skycast.home.data.remote

data class CitySearchDto(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)