package com.weather.skycast.home.data

import com.weather.skycast.core.data.remote.NetworkApi
import com.weather.skycast.core.data.remote.Resource
import com.weather.skycast.home.data.local.dao.WeatherDao
import com.weather.skycast.home.data.local.mapper.toForecastItem
import com.weather.skycast.home.data.local.mapper.toForecastItemEntity
import com.weather.skycast.home.data.local.mapper.toWeather
import com.weather.skycast.home.data.local.mapper.toWeatherEntity
import com.weather.skycast.home.data.mapper.toCitySearch
import com.weather.skycast.home.data.mapper.toWeather
import com.weather.skycast.home.domain.model.CitySearch
import com.weather.skycast.home.domain.model.Weather
import com.weather.skycast.home.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: NetworkApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())

        val localWeather = weatherDao.getAllWeather()
        val localForecast = weatherDao.getAllForecastItems().map { it.toForecastItem() }
        if (localWeather.isNotEmpty()) emit(
            Resource.Success(
                localWeather[0].toWeather(
                    localForecast
                )
            )
        )

        try {
            val remoteData = api.getHourlyForecast(latitude, longitude)

            if (remoteData.cod == "200") {
                val weather = remoteData.toWeather()
                emit(Resource.Success(weather))

                weatherDao.insertWeather(weather.toWeatherEntity())
                weatherDao.clearForecastItems()
                weatherDao.insertForecastItems(weather.forecast.map { it.toForecastItemEntity() })

            } else {
                emit(Resource.Error("Something went wrong..."))
            }
        } catch (e: UnresolvedAddressException) {
            emit(Resource.Error("No Internet"))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("No Internet"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("An unknown error occurred"))
        }
    }

    override suspend fun getCities(city: String): Flow<Resource<List<CitySearch>>> = flow {
        emit(Resource.Loading())

        try {
            val data = api.searchCity(cityName = city)

            if (data.isNotEmpty()) {
                emit(Resource.Success(data.map { it.toCitySearch() }))
            } else {
                emit(Resource.Error("No City Found"))
            }
        } catch (e: UnresolvedAddressException) {
            emit(Resource.Error("No Internet"))
        } catch (e: UnknownHostException) {
            emit(Resource.Error("No Internet"))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("An unknown error occurred"))
        }
    }
}