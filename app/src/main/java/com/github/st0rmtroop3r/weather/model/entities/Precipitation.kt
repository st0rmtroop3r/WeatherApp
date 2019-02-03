package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Precipitation volume.
 * Data class for OpenWeatherMapApi
 */
data class Precipitation(

    /** Precipitation volume for the last 1 hour */
    @SerializedName("1h")
    val oneHour: String?,

    /** Precipitation volume for the last 3 hours */
    @SerializedName("3h")
    val threeHours: String?
)