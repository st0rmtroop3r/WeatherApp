package com.github.st0rmtroop3r.weather.model.entities

import com.google.gson.annotations.SerializedName

data class WeatherList(

    @SerializedName("cnt")
    val count: Int,

    @SerializedName("list")
    val list: List<Weather>
)