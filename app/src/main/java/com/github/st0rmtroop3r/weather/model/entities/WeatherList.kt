package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherList(

    /** Weather list size */
    @SerializedName("cnt")
    val count: Int,

    /** Weather list */
    @SerializedName("list")
    val list: List<Weather>
)