package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    val currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeatherError = MutableLiveData<String>()

    fun updateData() {

        try {
            currentWeather.value = weatherRepository.getCurrentWeather("Kyiv")
        } catch (e: Exception) {
            Log.w(TAG, e.message, e)
            currentWeatherError.value = e.message
        }

    }
}
