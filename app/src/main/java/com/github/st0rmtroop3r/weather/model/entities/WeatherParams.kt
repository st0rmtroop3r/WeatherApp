package com.github.st0rmtroop3r.weather.model.entities

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherParams(
    val temp: Float,
    val pressure: Int,
    val humidity: Int,
    val temp_min: Float,
    val temp_max: Float
)