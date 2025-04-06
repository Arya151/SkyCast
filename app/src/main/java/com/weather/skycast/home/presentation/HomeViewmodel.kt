package com.weather.skycast.home.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.skycast.core.data.remote.Resource
import com.weather.skycast.core.domain.location.LocationTracker
import com.weather.skycast.core.presentation.getGradientForWeather
import com.weather.skycast.home.domain.repository.WeatherRepository
import com.weather.skycast.home.presentation.component.HomeAction
import com.weather.skycast.home.presentation.component.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchClick -> {
                _state.value = _state.value.copy(isSearching = !_state.value.isSearching)
            }

            is HomeAction.OnSearchTextChange -> {
                _state.value = _state.value.copy(
                    searchText = action.text,
                    isLoading = false,
                    isSearching = true
                )
            }

            is HomeAction.SubmitSearch -> {
                loadCities(action.city)
            }

            is HomeAction.OnCitySelected -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loadWeather(lat = action.lat, lon = action.lon)
                    _state.value = _state.value.copy(
                        searchText = "",
                        isSearching = false
                    )
                }
            }

            is HomeAction.OnBackPressed -> {
                if (_state.value.isSearching) {
                    _state.update {
                        it.copy(
                            isSearching = false
                        )
                    }
                }
            }
        }
    }

    fun loadWeatherInfo() {
        Log.d("viewmodel", "loadWeatherInfo called")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = ""
                )
            }
            locationTracker.getCurrentLocation()?.let { location ->
                loadWeather(location.latitude, location.longitude)
            } ?: run {
                Log.d("viewmodel", "loadWeatherInfo failed ")

                _state.update {
                    it.copy(
                        isLoading = false,
                        filteredCities = emptyList(),
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS "
                    )
                }
            }
        }
    }

    private suspend fun loadWeather(lat: Double, lon: Double) {
        repository.getWeatherData(lat, lon).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val data = result.data
                    _state.update {
                        Log.d("viewmodel", "loadWeatherInfo success - $data")

                        it.copy(
                            cityName = data?.city ?: "",
                            temperature = (data?.temp ?: "-").toString(),
                            feelsLike = (data?.feelsLike ?: "-").toString(),
                            pressure = (data?.pressure ?: "-").toString(),
                            seaLevel = (data?.seaLevel ?: "-").toString(),
                            forecastList = data?.forecast ?: emptyList(),
                            gradientColors = getGradientForWeather(data?.temp ?: -1),
                            isLoading = false,
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            filteredCities = emptyList(),
                            error = result.message ?: "Something went wrong...",
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            forecastList = emptyList(),
                            isLoading = true,
                            error = ""
                        )
                    }
                }
            }
        }

    }

    private fun loadCities(city: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    error = ""
                )
            }
            repository.getCities(city = city).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                filteredCities = emptyList(),
                                error = result.message ?: "Something went wrong",
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                error = "",
                                filteredCities = emptyList()
                            )
                        }
                    }

                    is Resource.Success -> {
                        val data = result.data
                        _state.update {
                            it.copy(
                                error = "",
                                filteredCities = data ?: emptyList(),
                            )
                        }
                    }
                }
            }

        }
    }
}