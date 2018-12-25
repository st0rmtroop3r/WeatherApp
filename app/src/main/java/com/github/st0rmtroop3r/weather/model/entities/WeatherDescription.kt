package com.github.st0rmtroop3r.weather.model.entities

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherDescription(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)