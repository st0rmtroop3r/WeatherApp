package com.github.st0rmtroop3r.weather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Query("SELECT * FROM cities")
    fun getCities() : LiveData<List<City>>

    @Delete
    fun removeCity(city: City)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(weather: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeatherList(weatherList: List<Weather>)

    @Query("SELECT * FROM weather")
    fun getWeatherList(): LiveData<List<Weather>>

    @Delete
    fun delete(weather: Weather)

}