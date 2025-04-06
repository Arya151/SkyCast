package com.weather.skycast.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.skycast.home.data.local.entity.ForecastItemEntity
import com.weather.skycast.home.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastItems(forecast: List<ForecastItemEntity>)

    @Query("SELECT * FROM weather")
    suspend fun getAllWeather(): List<WeatherEntity>

    @Query("SELECT * FROM forecast_items")
    suspend fun getAllForecastItems(): List<ForecastItemEntity>

    @Query("DELETE FROM forecast_items")
    suspend fun clearForecastItems()
}