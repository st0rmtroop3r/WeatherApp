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

/**
 * Removes Weather data and all data related to Weather city id from SQLite database
 */

class DeleteWeatherUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository
    ) {

    /**
     * Starts delayed Weather deletion using the given coroutine scope
     *
     * @param weather the Weather to delete
     * @param scope coroutine scope that will be used for background DB access
     * @param delay wait time before deleting marked for deletion Weather (in milliseconds)
     * @onSuccess (optional) callback to invoke after the Weather was successfully marked for deletion
     * @onError (optional) callback to invoke if an exception occurred while marking for deletion
     */
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

    /**
     * Removes deletion mark for given Weather. This method does not cancel scheduled deletion job
     *
     * @param weather the weather to remove from marked for deletion list
     * @param scope coroutine scope that will be used for background DB access
     * @onSuccess (optional) callback to invoke after the Weather was successfully unmarked for deletion
     * @onError (optional) callback to invoke if an exception occurred while unmarking for deletion
     */
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