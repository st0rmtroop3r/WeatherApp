package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

/**
 *  Data class for OpenWeatherMapApi
 */
data class WeatherParams(

    /** Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit. */
    @SerializedName("temp")
    val temperature: Float,

    /** Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa */
    @SerializedName("pressure")
    val pressure: Float,

    /** Humidity, % */
    @SerializedName("humidity")
    val humidity: Int,

    /** Minimum temperature at the moment. This is deviation from current temp that is possible
     * for large citiesLiveData and megalopolises geographically expanded (use these parameter optionally).
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit. */
    @SerializedName("temp_min")
    val temperatureMin: Float,

    /** Maximum temperature at the moment. This is deviation from current temp that is possible
     * for large citiesLiveData and megalopolises geographically expanded (use these parameter optionally).
     * Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit. */
    @SerializedName("temp_max")
    val temperatureMax: Float,

    /** Atmospheric pressure on the sea level, hPa */
    @SerializedName("sea_level")
    val seaLevel: Float,

    /** Atmospheric pressure on the ground level, hPa */
    @SerializedName("grnd_level")
    val groundLevel: Float
)