package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    val currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeatherError = MutableLiveData<String>()

    fun updateData() {

        try {
            currentWeather.value = GetCurrentWeatherUseCase("Kyiv").execute()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            currentWeatherError.value = e.message
        }

    }
}
