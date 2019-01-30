package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
data class Wind(

    /** Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour. */
    @SerializedName("speed")
    val speed: Float,

    /** Wind direction, degrees (meteorological) */
    @SerializedName("deg")
    val degrees: Float
)