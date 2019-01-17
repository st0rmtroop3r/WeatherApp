package com.github.st0rmtroop3r.weather.model.repository

import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse

interface WeatherRepository {

    fun addCity(city: City)

    fun getCities(): List<City>

    fun removeCity(city: City)

    fun getCachedWeather(citiesIds: List<Long>): List<WeatherResponse>

    fun getCurrentWeather(cityName: String): WeatherResponse

}