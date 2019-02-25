package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.entities.WeatherListOrder
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

/**
 * Saves Weather items order to SQLite DB
 */

class SaveWeatherListOrderUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository
    ){

    /**
     * Takes a list of Weather and saves its order to the DB
     *
     * @param weatherList the list of Weather which order must be saved
     * @param onSuccess (optional) callback to invoke if order successfully saved
     * @param onError (optional) callback to invoke if exception occurred
     */
    fun execute(
        weatherList: ArrayList<Weather>,
        onSuccess: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ): Job {

        val order = ArrayList<WeatherListOrder>()
        var orderNumber = 0
        weatherList.forEach {
            order.add(WeatherListOrder(it.cityId, ++orderNumber))
        }

        return saveWeatherListOrder(order)
    }

    private fun saveWeatherListOrder(
        orders: ArrayList<WeatherListOrder>,
        onSuccess: (() -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    ) = CoroutineScope(Dispatchers.IO).launch {
        try {
            weatherRepository.saveWeatherListOrder(orders)
            onSuccess?.invoke()
        } catch (e: Exception) {
            Log.e(TAG, "saveWeatherListOrder: ", e)
            onError?.invoke(e)
        }
    }

    companion object {
        val TAG: String = SaveWeatherListOrderUseCase::class.java.simpleName
    }
}