package com.weather.skycast.home.domain.model

data class ForecastItem(
    val dayLabel: String,
    val date: String,
    val temperature: String,
    val iconRes: Int
)