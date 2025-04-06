package com.weather.skycast.home.data.mapper

import com.weather.skycast.R
import com.weather.skycast.home.data.remote.WeatherItemDto
import com.weather.skycast.home.data.remote.WeatherSummaryDto
import com.weather.skycast.home.domain.model.ForecastItem
import com.weather.skycast.home.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun WeatherSummaryDto.toWeather(): Weather {
    val currentItem = list.firstOrNull()

    return Weather(
        city = city.name,
        temp = ((currentItem?.main?.temp ?: 0.0) - 273.15).toInt(),
        feelsLike = ((currentItem?.main?.feels_like ?: 0.0) - 273.15).toInt(),
        pressure = currentItem?.main?.pressure ?: 0,
        seaLevel = currentItem?.main?.sea_level ?: 0,
        forecast = list.toDailyForecast()
    )
}


fun List<WeatherItemDto>.toDailyForecast(): List<ForecastItem> {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    return this
        .groupBy { item ->
            sdf.format(Date(item.dt * 1000L)) // Group by day
        }
        .entries
        .sortedBy { it.key } // Ensure order: yesterday → future
        .take(4) // Take 4 days (yesterday, today, next 2)
        .mapIndexed { index, entry ->
            val temps = entry.value.map { it.main.temp - 273.15 }
            val avgTemp = temps.average().toInt()
            val icon = entry.value.firstOrNull()?.weather?.firstOrNull()?.icon

            ForecastItem(
                dayLabel = when (index) {
                    0 -> "Today"
                    1 -> "Tomorrow"
                    else -> getDayLabel(entry.value.first().dt)
                },
                date = entry.key,
                temperature = "$avgTemp",
                iconRes = mapWeatherIconToDrawable(icon)
            )
        }
}

fun getDayLabel(epochSeconds: Int): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = epochSeconds * 1000L
    return calendar.getDisplayName(
        Calendar.DAY_OF_WEEK,
        Calendar.LONG,
        Locale.getDefault()
    ) ?: "Day"
}

fun mapWeatherIconToDrawable(iconCode: String?): Int {
    return when (iconCode) {
        "01d", "01n" -> R.drawable.ic_sunny
        "02d", "02n" -> R.drawable.ic_sunnycloudy
        "03d", "03n", "04d", "04n" -> R.drawable.ic_cloudy
        "09d", "09n" -> R.drawable.ic_rainy
        "10d", "10n" -> R.drawable.ic_sunnyrainy
        "11d", "11n" -> R.drawable.ic_rainythunder
        "13d", "13n" -> R.drawable.ic_snowy
        "50d", "50n" -> R.drawable.ic_windy // should be foggy
        else -> R.drawable.ic_unknown
    }
}