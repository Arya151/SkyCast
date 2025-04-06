package com.weather.skycast.home.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.weather.skycast.home.domain.model.ForecastItem

@Composable
fun ForecastRow(item: ForecastItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = item.dayLabel,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = item.date, fontSize = 12.sp,
                color = Color.White
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${item.temperature}°C",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
        }
    }
}