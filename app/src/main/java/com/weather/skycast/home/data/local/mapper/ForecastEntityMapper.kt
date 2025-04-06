package com.weather.skycast.home.data.local.mapper

import com.weather.skycast.home.data.local.entity.ForecastItemEntity
import com.weather.skycast.home.domain.model.ForecastItem

fun ForecastItemEntity.toForecastItem(): ForecastItem {
    return ForecastItem(
        dayLabel = this.dayLabel,
        date = this.date,
        temperature = temperature,
        iconRes = iconRes
    )
}

fun ForecastItem.toForecastItemEntity(): ForecastItemEntity {
    return ForecastItemEntity(
        dayLabel = this.dayLabel,
        date = this.date,
        temperature = temperature,
        iconRes = iconRes,
    )
}
