package com.github.st0rmtroop3r.weather.viewmodel.usecase

import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import javax.inject.Inject

class GetCachedWeatherUseCase(val cities: List<City>) {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        WeatherApp.appComponent.inject(this)
    }

    fun execute(): List<WeatherResponse> {
        val citiesIds = ArrayList<Long>()
        cities.forEach { city: City -> citiesIds.add(city.id) }
        return weatherRepository.getCachedWeather(citiesIds)
    }
}