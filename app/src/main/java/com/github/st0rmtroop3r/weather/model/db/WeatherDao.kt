package com.github.st0rmtroop3r.weather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.DeletedWeather
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

    @Query("SELECT * FROM weather LEFT JOIN deleted ON city_id = id WHERE city_id IS NULL")
    fun getWeatherList(): LiveData<List<Weather>>

    @Delete
    fun delete(weather: Weather)

    @Query("DELETE FROM weather WHERE id IN (SELECT city_id FROM deleted)")
    fun deleteMarkedWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun markForDeletion(deletedWeather: DeletedWeather)

    @Delete
    fun removeDeletionRecord(deletedWeather: DeletedWeather)

}