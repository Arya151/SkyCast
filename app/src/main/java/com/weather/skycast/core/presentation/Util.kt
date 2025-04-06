package com.weather.skycast.core.presentation

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.ui.graphics.Color
import com.weather.skycast.ui.theme.BlueDark
import com.weather.skycast.ui.theme.BlueLight

fun getGradientForWeather(temp: Int): List<Color> {
    return when {
        temp <= 10 -> listOf(Color(0xFF22988A),Color(0xFF045048))
        temp in 11..25 -> listOf(Color(0xFF56CCF2), Color(0xFF2F80ED))
        temp > 25 -> listOf(Color(0xFFEF4F1E), Color(0xFF9B1417))
        else -> listOf(BlueLight, BlueDark) // Default
    }
}
