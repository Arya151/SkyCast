package com.weather.skycast.home.domain.model

data class CitySearch(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)