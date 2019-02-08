package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Adds Weather data to SQLite database
 */

class AddWeatherUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository
    ) {

    /**
     * Launches a coroutine job to add given Weather to DB storage
     *
     * @param weather the weather to add to DB
     * @param scope scope for background DB access
     * @param onResult (optional) callback to invoke when operation succeed or failed
     */
    fun execute(
        weather: Weather?,
        scope: CoroutineScope,
        onResult: ((Exception?) -> Unit)? = null
    ) {

        scope.launch(Dispatchers.IO) {

            try {
                weather?.also {
                    weatherRepository.addWeather(it)
                } ?: throw NullPointerException("weather is NULL")
                onResult?.invoke(null)
            } catch (e: Exception) {
                Log.e(TAG, "execute: ", e)
                onResult?.invoke(e)
            }
        }
    }

    companion object {
        val TAG = AddWeatherUseCase::class.java.simpleName
    }
}