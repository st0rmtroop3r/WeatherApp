package com.github.st0rmtroop3r.weather.viewmodel.usecase

import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase(val cityName: String) {

    @Inject
    lateinit var weatherRepository : WeatherRepository

    init {
        WeatherApp.appComponent.inject(this)
    }

    fun execute(): WeatherResponse {
        return weatherRepository.getCurrentWeather(cityName)
    }
}