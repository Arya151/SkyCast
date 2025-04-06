package com.weather.skycast.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.skycast.home.presentation.component.HomeAction
import com.weather.skycast.home.presentation.component.HomeUiState

@Composable
fun WeatherScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${state.temperature}°C",
            fontSize = 48.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        if (state.forecastList.size > 2) {
            Icon(
                painter = painterResource(id = state.forecastList.get(1).iconRes),
                contentDescription = "Weather Icon",
                modifier = Modifier
                    .size(98.dp),
                tint = Color.Unspecified
            )
        }

    }

    Text(
        text = "Feels like ${state.feelsLike}°C",
        fontSize = 16.sp,
        color = Color.White
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Pressure: ${state.pressure} hPa",
            color = Color.White
        )
        Text(
            text = "Sea Level: ${state.seaLevel} m",
            color = Color.White
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(state.forecastList) { item ->
            ForecastRow(item)
            HorizontalDivider(color = Color.White)
        }
    }
}
