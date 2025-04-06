package com.weather.skycast.home.data.local.mapper

import com.weather.skycast.home.data.local.entity.WeatherEntity
import com.weather.skycast.home.domain.model.ForecastItem
import com.weather.skycast.home.domain.model.Weather

fun WeatherEntity.toWeather(forecast: List<ForecastItem>): Weather {
    return Weather(
        city = city,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        seaLevel = seaLevel,
        forecast = forecast
    )
}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        city = city,
        temp = temp,
        feelsLike = feelsLike,
        pressure = pressure,
        seaLevel = seaLevel
    )
}