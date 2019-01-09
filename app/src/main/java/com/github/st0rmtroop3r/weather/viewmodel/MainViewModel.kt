package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel @Inject constructor(val api : OpenWeatherMapApi) : ViewModel() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    val currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeatherError = MutableLiveData<String>()

    fun updateData() {
        val deferredWeather = api.getWeather("Kyiv")
        runBlocking {
            try {
                deferredWeather.await()
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
        if (deferredWeather.isCompletedExceptionally) {
            currentWeatherError.value = deferredWeather.getCompletionExceptionOrNull()?.message
        } else {
            currentWeather.value = deferredWeather.getCompleted()
        }

    }
}
