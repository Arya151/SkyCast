package com.weather.skycast.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val id: Int = 0,
    val city: String,
    val temp: Int,
    val feelsLike: Int,
    val pressure: Int,
    val seaLevel: Int
)