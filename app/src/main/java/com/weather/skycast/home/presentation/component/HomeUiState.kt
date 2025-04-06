package com.weather.skycast.home.presentation.component

import androidx.compose.ui.graphics.Color
import com.weather.skycast.home.domain.model.CitySearch
import com.weather.skycast.home.domain.model.ForecastItem
import com.weather.skycast.ui.theme.BlueDark
import com.weather.skycast.ui.theme.BlueLight

data class HomeUiState(
    val cityName: String = "",
    val temperature: String = "",
    val feelsLike: String = "",
    val pressure: String = "",
    val seaLevel: String = "",
    val gradientColors: List<Color> = listOf(BlueLight, BlueDark),
    val forecastList: List<ForecastItem> = emptyList(),
    val searchText: String = "",
    val filteredCities: List<CitySearch> = emptyList(),
    val isSearching: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
)