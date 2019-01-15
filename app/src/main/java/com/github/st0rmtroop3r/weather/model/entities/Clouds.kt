package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
@Entity
data class Clouds(

    /**
     * Cloudiness, %
     */
    @SerializedName("all")
    val percentage: Int
)