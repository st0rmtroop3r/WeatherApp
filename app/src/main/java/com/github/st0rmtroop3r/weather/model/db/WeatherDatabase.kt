package com.github.st0rmtroop3r.weather.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse

@Database(version = 1, entities = arrayOf(WeatherResponse::class))
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getWeatherDao() : WeatherDao
}