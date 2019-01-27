package com.github.st0rmtroop3r.weather.model.repository

import com.github.st0rmtroop3r.weather.model.db.WeatherDao
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository

    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val openWeatherMapApi : OpenWeatherMapApi
    ) {

    suspend fun addCity(city: City) {
        withContext(Dispatchers.IO) {
            weatherDao.addCity(city)
        }
    }

    fun citiesLiveData() = weatherDao.getCities()

    fun removeCity(city: City) = weatherDao.removeCity(city)

    fun weatherList() = weatherDao.getWeatherList()

    suspend fun deleteWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            weatherDao.delete(weather)
        }
    }

    suspend fun updateCurrentWeather(citiesIds: String) {
        val weatherList = requestCurrentWeather(citiesIds)
        weatherDao.saveWeatherList(weatherList)
    }

    private suspend fun requestCurrentWeather(citiesIds: String): List<Weather> {

        val deferredWeatherList = openWeatherMapApi.getWeathers(citiesIds)
        deferredWeatherList.await()
        return deferredWeatherList.getCompleted().list
    }

    companion object {
        val TAG = WeatherRepository::class.java.simpleName
    }

}