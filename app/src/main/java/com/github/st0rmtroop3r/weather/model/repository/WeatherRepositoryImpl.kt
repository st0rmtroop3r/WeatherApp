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

class WeatherRepositoryImpl

    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val openWeatherMapApi : OpenWeatherMapApi
    ) : WeatherRepository {

    companion object {
        val tag = WeatherRepositoryImpl::class.java.simpleName
    }

    override fun addCity(city: City) = asyncAwait {
        weatherDao.addCity(city)
    }

    override fun getCities() = asyncAwait {
        weatherDao.getCities()
    }

    override fun removeCity(city: City) = asyncAwait {
        weatherDao.removeCity(city)
    }

    override fun getCachedWeather(citiesIds: List<Long>): List<WeatherResponse> {
        // todo
        return listOf()
    }

    override fun getCurrentWeather(cityName: String): WeatherResponse {
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