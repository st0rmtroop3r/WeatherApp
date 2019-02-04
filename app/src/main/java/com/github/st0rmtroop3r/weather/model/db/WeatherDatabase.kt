package com.github.st0rmtroop3r.weather.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.DeletedWeather
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.entities.WeatherListOrder

@Database(
    version = 1,
    entities = [City::class, Weather::class, DeletedWeather::class, WeatherListOrder::class])
@TypeConverters(RoomTypeConverter::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getWeatherDao() : WeatherDao
}