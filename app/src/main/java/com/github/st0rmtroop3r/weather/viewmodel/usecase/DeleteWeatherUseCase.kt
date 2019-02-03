package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.DeletedWeather
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteWeatherUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository
    ) {

    fun execute(
        weather: Weather,
        scope: CoroutineScope,
        delay: Long,
        onSuccess: ((Weather) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ) = scope.launch(Dispatchers.IO) {
        try {
            weatherRepository.deleteWeatherDelayed(weather.cityId, delay + 500)
            onSuccess?.invoke(weather)
        } catch (e: Exception) {
            Log.e(TAG, "executeDelayed: ", e)
            onError?.invoke(e)
        }
    }

    fun undoDelete(weather: Weather,
                   scope: CoroutineScope,
                   onSuccess: ((Weather) -> Unit)? = null,
                   onError: ((Exception) -> Unit)? = null
    ) = scope.launch(Dispatchers.IO) {
        try {
            weatherRepository.removeDeletionRecord(DeletedWeather(weather.cityId))
            onSuccess?.invoke(weather)
        } catch (e: Exception) {
            Log.e(TAG, "undoDelete: ", e)
            onError?.invoke(e)
        }
    }

    companion object {
        val TAG: String = DeleteWeatherUseCase::class.java.simpleName
    }
}