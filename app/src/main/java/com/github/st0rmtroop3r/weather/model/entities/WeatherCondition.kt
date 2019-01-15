package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
@Entity
data class WeatherCondition(

    /**
     * Weather condition id
     */
    @SerializedName("id")
    val id: Long,

    /**
     * Group of weather parameters (Rain, Snow, Extreme etc.)
     */
    @SerializedName("main")
    val main: String,

    /**
     * Weather condition within the group
     */
    @SerializedName("description")
    val description: String,

    /**
     * Weather icon id
     */
    @SerializedName("icon")
    val icon: String
)