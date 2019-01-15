package com.github.st0rmtroop3r.weather.model.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 *  City geo location.
 *  Data class for OpenWeatherMapApi
 */
@Entity
data class Coordinates(

    /**
     * City geo location, longitude
     */
    @SerializedName("lon")
    val longitude: Float,

    /**
     * City geo location, latitude
     */
    @SerializedName("lat")
    val latitude: Float
)