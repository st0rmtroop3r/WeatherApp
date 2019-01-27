package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateCurrentWeatherUseCase
    @Inject
    constructor(private val weatherRepository : WeatherRepository) {

    fun execute(cities: List<City>?, scope: CoroutineScope) {

        Log.d(TAG, "citiesList: ${cities?.toString()}")
        if (cities == null || cities.isEmpty()) { return }

        val stringBuilder = StringBuilder()
        cities.forEach { stringBuilder.append(it.id).append(",") }
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)

        scope.launch(Dispatchers.IO) {
            weatherRepository.updateCurrentWeather(stringBuilder.toString())
        }
    }

    companion object {
        val TAG = UpdateCurrentWeatherUseCase::class.java.simpleName
    }
}