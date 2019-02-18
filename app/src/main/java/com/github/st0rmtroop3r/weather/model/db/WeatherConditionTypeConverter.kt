package com.github.st0rmtroop3r.weather.model.db

import androidx.room.TypeConverter
import com.github.st0rmtroop3r.weather.model.entities.WeatherCondition
import com.google.gson.Gson

class WeatherConditionTypeConverter {

    @TypeConverter
    fun fromList(list: List<WeatherCondition>?): String? = Gson().toJson(list)

    @TypeConverter
    fun toList(value: String?): List<WeatherCondition>? = if (value == null || value == "null") null
        else Gson().fromJson<Array<WeatherCondition>>(value, Array<WeatherCondition>::class.java)
            .toList()

}