package com.github.st0rmtroop3r.weather.model.repository

import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse

interface WeatherRepository {

    fun getCurrentWeather(cityName: String): WeatherResponse

}