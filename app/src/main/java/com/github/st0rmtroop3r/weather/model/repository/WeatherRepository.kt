package com.github.st0rmtroop3r.weather.model.repository

import com.github.st0rmtroop3r.weather.model.db.WeatherDao
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WeatherRepository

    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val openWeatherMapApi : OpenWeatherMapApi
    ) {

    companion object {
        val tag = WeatherRepository::class.java.simpleName
    }

    fun addCity(city: City) = asyncAwait {
        weatherDao.addCity(city)
    }

    fun getCities() = asyncAwait {
        weatherDao.getCities()
    }

    fun removeCity(city: City) = asyncAwait {
        weatherDao.removeCity(city)
    }

    fun getCachedWeather(citiesIds: List<Long>): List<WeatherResponse> {
        // todo
        return listOf()
    }

    fun getCurrentWeather(cityName: String): WeatherResponse {
        val deferredWeather = openWeatherMapApi.getWeather(cityName)
        runBlocking {
            deferredWeather.await()
        }
        return deferredWeather.getCompleted()
    }

    fun <T> asyncAwait(block: suspend CoroutineScope.() -> T) = runBlocking{
        GlobalScope.async {
            return@async block.invoke(this)
        }.await()
    }

}