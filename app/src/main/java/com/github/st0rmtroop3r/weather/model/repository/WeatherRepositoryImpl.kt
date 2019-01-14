package com.github.st0rmtroop3r.weather.model.repository

import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WeatherRepositoryImpl

    @Inject
    constructor(
        private val openWeatherMapApi : OpenWeatherMapApi
    ) : WeatherRepository {

    companion object {
        val tag = WeatherRepositoryImpl::class.java.simpleName
    }

    override fun getCurrentWeather(cityName: String): WeatherResponse {
        val deferredWeather = openWeatherMapApi.getWeather(cityName)
        runBlocking {
            deferredWeather.await()
        }
        return deferredWeather.getCompleted()
    }

}