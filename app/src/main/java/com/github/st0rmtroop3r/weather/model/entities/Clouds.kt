package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
data class Clouds(

    /**
     * Cloudiness, %
     */
    @SerializedName("all")
    val percentage: Int
)