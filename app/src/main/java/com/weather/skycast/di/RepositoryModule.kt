package com.weather.skycast.di

import androidx.compose.runtime.ExperimentalComposeApi
import com.weather.skycast.home.data.WeatherRepositoryImpl
import com.weather.skycast.home.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@ExperimentalComposeApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

}