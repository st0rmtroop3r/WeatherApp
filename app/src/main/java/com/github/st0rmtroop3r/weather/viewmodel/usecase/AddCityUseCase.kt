package com.github.st0rmtroop3r.weather.viewmodel.usecase

import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCityUseCase
    @Inject
    constructor(private val weatherRepository: WeatherRepository) {

    fun execute(city: City, scope: CoroutineScope) {
        scope.launch {
            weatherRepository.addCity(city)
        }
    }
}