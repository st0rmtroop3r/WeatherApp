package com.github.st0rmtroop3r.weather.model.db

import androidx.room.TypeConverter
import com.github.st0rmtroop3r.weather.model.entities.WeatherCondition
import com.google.gson.Gson

class RoomTypeConverter {

    @TypeConverter
    fun fromList(list: List<WeatherCondition>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(value: String?): List<WeatherCondition>? {
        value?.let {
            return Gson().fromJson<List<WeatherCondition>>(value, ArrayList<WeatherCondition>().javaClass)
        }
        return arrayListOf()
    }
}