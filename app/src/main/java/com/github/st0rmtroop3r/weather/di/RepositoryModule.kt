package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger2 module that provides repositories
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(implementation: WeatherRepositoryImpl): WeatherRepository {
        return implementation
    }
}