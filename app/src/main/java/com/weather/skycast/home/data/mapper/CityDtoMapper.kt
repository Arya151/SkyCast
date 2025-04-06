package com.weather.skycast.home.data.mapper

import com.weather.skycast.home.data.remote.CitySearchDto
import com.weather.skycast.home.domain.model.CitySearch

fun CitySearchDto.toCitySearch(): CitySearch {
    return CitySearch(
        country = country,
        lat = lat,
        lon = lon,
        name = name,
        state = state
    )
}