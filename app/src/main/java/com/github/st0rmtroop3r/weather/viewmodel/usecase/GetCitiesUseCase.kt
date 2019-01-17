package com.github.st0rmtroop3r.weather.viewmodel.usecase

import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import javax.inject.Inject

class GetCitiesUseCase {

    @Inject
    lateinit var weatherRepository: WeatherRepository

    init {
        WeatherApp.appComponent.inject(this)
    }

    fun execute() = weatherRepository.getCities()
}