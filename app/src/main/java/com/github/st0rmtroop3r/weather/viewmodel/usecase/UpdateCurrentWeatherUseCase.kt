package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Updates current weather data
 */

class UpdateCurrentWeatherUseCase
    @Inject
    constructor(private val weatherRepository : WeatherRepository) {

    /**
     * Request current weather data for a given Weather list
     *
     * @param weatherList the Weather list to update
     * @param scope scope for background network access
     * @param onSuccess callback to invoke on request succeed
     * @param onError callback to invoke on exception occurred
     */
    fun execute(
        weatherList: List<Weather>?,
        scope: CoroutineScope,
        onSuccess: (() -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {

        if (weatherList.isNullOrEmpty()) return

        val stringBuilder = StringBuilder()
        weatherList.forEach { stringBuilder.append(it.cityId).append(",") }
        stringBuilder.deleteCharAt(stringBuilder.lastIndex)

        scope.launch(Dispatchers.IO) {
            try {
                weatherRepository.updateCurrentWeather(stringBuilder.toString())
                onSuccess?.invoke()
            } catch (e: Exception) {
                Log.e(TAG, "execute: ", e)
                onError?.invoke(e)
            }
        }
    }

    companion object {
        val TAG = UpdateCurrentWeatherUseCase::class.java.simpleName
    }
}