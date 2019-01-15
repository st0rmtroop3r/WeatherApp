package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
@Entity
data class WeatherSystem(

    /**
     * Internal parameter
     */
    @SerializedName("type")
    val type: Int,

    /**
     * Internal parameter
     */
    @SerializedName("id")
    val id: Long,

    /**
     * Internal parameter
     */
    @SerializedName("message")
    val message: Float,

    /**
     * Country code (GB, JP etc.)
     */
    @SerializedName("country")
    val countryCode: String,

    /**
     * Sunrise time, unix, UTC
     */
    @SerializedName("sunrise")
    val sunrise: Long,

    /**
     * Sunset time, unix, UTC
     */
    @SerializedName("sunset")
    val sunset: Long
)