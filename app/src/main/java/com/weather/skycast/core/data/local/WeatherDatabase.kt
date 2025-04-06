package com.weather.skycast.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.skycast.home.data.local.dao.WeatherDao
import com.weather.skycast.home.data.local.entity.ForecastItemEntity
import com.weather.skycast.home.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, ForecastItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}