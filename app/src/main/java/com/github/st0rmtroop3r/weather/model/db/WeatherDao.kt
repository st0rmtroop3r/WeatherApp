package com.github.st0rmtroop3r.weather.model.db

import androidx.room.*
import com.github.st0rmtroop3r.weather.model.entities.City

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Query("SELECT * FROM cities")
    fun getCities() : List<City>

    @Delete
    fun removeCity(city: City)
}