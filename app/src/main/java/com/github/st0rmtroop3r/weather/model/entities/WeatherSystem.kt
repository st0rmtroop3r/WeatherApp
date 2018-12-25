package com.github.st0rmtroop3r.weather.model.entities

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherSystem(
    val type: Int,
    val id: Long,
    val message: Float,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)