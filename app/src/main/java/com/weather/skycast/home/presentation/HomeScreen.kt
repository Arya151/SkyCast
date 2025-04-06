package com.weather.skycast.home.presentation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.skycast.R
import com.weather.skycast.home.domain.model.CitySearch
import com.weather.skycast.home.domain.model.ForecastItem
import com.weather.skycast.home.presentation.component.HomeAction
import com.weather.skycast.home.presentation.component.HomeUiState

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {

    val activity = LocalActivity.current

    Column(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = state.gradientColors
                )
            )
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {

        // Top Row: City + Search
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state.isSearching) {
                SearchScreen(
                    state = state,
                    onAction = onAction
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = state.cityName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onAction(HomeAction.OnSearchClick) }
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(
                    onClick = { onAction(HomeAction.OnRefreshClick) }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = Color.White)
                }
            }

        }

        if (!state.isSearching) {
            WeatherScreen(
                state = state,
                onAction = onAction
            )
        }

    }
    BackHandler {
        if (state.isSearching) {
            onAction(HomeAction.OnBackPressed)
        } else {
            activity?.finish()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val mockState = HomeUiState(
        cityName = "Mumbai",
        temperature = "30",
        feelsLike = "33",
        pressure = "1010",
        seaLevel = "7",
        isSearching = false,
        filteredCities = listOf(
            CitySearch(
                name = "New York",
                state = "New York",
                country = "US",
                lat = 40.7128,
                lon = -74.0060
            ),
            CitySearch(
                name = "London",
                state = "England",
                country = "GB",
                lat = 51.5074,
                lon = -0.1278
            ),
            CitySearch(
                name = "Tokyo",
                state = "Tokyo",
                country = "JP",
                lat = 35.6895,
                lon = 139.6917
            ),
            CitySearch(
                name = "Mumbai",
                state = "Maharashtra",
                country = "IN",
                lat = 19.0760,
                lon = 72.8777
            ),
            CitySearch(
                name = "Paris",
                state = "Île-de-France",
                country = "FR",
                lat = 48.8566,
                lon = 2.3522
            )
        ),
        forecastList = listOf(
            ForecastItem("Yesterday", "Apr 5", "29", R.drawable.ic_sunny),
            ForecastItem("Today", "Apr 6", "30", R.drawable.ic_cloudy),
            ForecastItem("Tomorrow", "Apr 7", "31", R.drawable.ic_rainy),
            ForecastItem("Apr 8", "Apr 8", "32", R.drawable.ic_sunny)
        )
    )

    HomeScreen(
        state = mockState,
        onAction = {}, // no-op for preview,
    )
}