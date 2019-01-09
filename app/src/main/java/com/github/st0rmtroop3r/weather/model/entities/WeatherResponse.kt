package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherResponse (

    /**
     * City geo location
     */
    @SerializedName("coord")
    val coordinates: Coordinates,

    /**
     * Weather conditions
     */
    @SerializedName("weather")
    val weather: List<WeatherCondition>,

    /**
     * Internal parameter
     */
    @SerializedName("base")
    val base: String,

    /**
     * Weather main params
     */
    @SerializedName("main")
    val main: WeatherParams,

    /**
     * Visibility, meter
     */
    @SerializedName("visibility")
    val visibility: Int,

    /**
     * Wind speed and direction
     */
    @SerializedName("wind")
    val wind: Wind,

    /**
     * Cloudiness
     */
    @SerializedName("clouds")
    val clouds: Clouds,

    /**
     * Rain volume
     */
    @SerializedName("rain")
    val rain: Precipitation,

    /**
     * Snow volume
     */
    @SerializedName("snow")
    val snow: Precipitation,

    /**
     * Time of data calculation, unix, UTC
     */
    @SerializedName("dt")
    val dataTime: Long,

    @SerializedName("sys")
    val system: WeatherSystem,

    /**
     * City ID
     */
    @SerializedName("id")
    val cityId: Long,

    /**
     * City name
     */
    @SerializedName("name")
    val cityName: String,

    /**
     * Internal parameter
     */
    @SerializedName("cod")
    val cod: Int
)