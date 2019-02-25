package com.github.st0rmtroop3r.weather

import com.github.st0rmtroop3r.weather.model.db.WeatherConditionTypeConverter
import com.github.st0rmtroop3r.weather.model.entities.WeatherCondition
import org.junit.Test

class TypeConverterTest {

    private val converter = WeatherConditionTypeConverter()

    @Test
    fun test_conversion() {

        val list = ArrayList<WeatherCondition>()
        list.add(WeatherCondition(1, "main", "description", "icon"))
        var json = converter.fromList(list)
        var convertedList = converter.toList(json)
        assert(list == convertedList)

        list.add(WeatherCondition(Long.MAX_VALUE, "", "", ""))
        json = converter.fromList(list)
        convertedList = converter.toList(json)
        assert(list == convertedList)
    }

    @Test
    fun test_conversion_with_null() {

        val json = converter.fromList(null)
        val newList = converter.toList(json)
        assert(newList == null)
    }
}