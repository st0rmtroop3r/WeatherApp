package com.github.st0rmtroop3r.weather.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.st0rmtroop3r.weather.model.entities.DeletedWeather
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.entities.WeatherListOrder

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherList(weatherList: List<Weather>)

    @Query("SELECT * FROM weather LEFT JOIN deleted ON deleted.city_id = id LEFT JOIN weather_order ON weather_order.city_id = id WHERE deleted.city_id IS NULL ORDER BY order_number")
    fun getWeatherList(): LiveData<List<Weather>>

    @Delete
    suspend fun delete(weather: Weather)

    @Query("DELETE FROM weather WHERE id IN (SELECT city_id FROM deleted)")
    suspend fun deleteMarkedWeather()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markForDeletion(deletedWeather: DeletedWeather)

    @Delete
    suspend fun removeDeletionRecord(deletedWeather: DeletedWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherListOrder(orders: List<WeatherListOrder>)

}