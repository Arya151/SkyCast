package com.weather.skycast.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "forecast_items"
)
data class ForecastItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayLabel: String,
    val date: String,
    val temperature: String,
    val iconRes: Int
)