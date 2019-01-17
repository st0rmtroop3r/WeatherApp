package com.github.st0rmtroop3r.weather.di

import androidx.room.Room
import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.db.WeatherDao
import com.github.st0rmtroop3r.weather.model.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbRepository {

    @Provides
    @Singleton
    fun provideWeatherDao() : WeatherDao {
        return Room.databaseBuilder(WeatherApp.appContext, WeatherDatabase::class.java, "weather-db")
            .build()
            .getWeatherDao()
    }
}