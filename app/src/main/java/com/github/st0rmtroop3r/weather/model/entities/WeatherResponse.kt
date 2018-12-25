package com.github.st0rmtroop3r.weather.model.entities

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherResponse (
    val coord: Coordinates,
    val weather: List<WeatherDescription>,
    val base: String,
    val main: WeatherParams,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: WeatherSystem,
    val id: Long,
    val name: String,
    val cod: Int
)