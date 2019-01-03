package com.github.st0rmtroop3r.weather.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.di.DaggerAppComponent
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainViewModel : ViewModel() {

    private val TAG = this.javaClass.simpleName

    @Inject
    lateinit var api : OpenWeatherMapApi

    private val currentWeather = MutableLiveData<WeatherResponse>()
    private val currentWeatherError = MutableLiveData<String>()

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getCurrentWeather() = currentWeather

    fun getCurrentWeatherError() = currentWeatherError

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
