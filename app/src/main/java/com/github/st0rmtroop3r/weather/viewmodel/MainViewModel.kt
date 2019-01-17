package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddCityUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCachedWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCitiesUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(val weatherRepository: WeatherRepository): ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    val currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeatherError = MutableLiveData<String>()

    fun onResume() {
        updateData()
    }

    fun updateData() {

        AddCityUseCase(City(703448, "Kyiv")).execute()

        val cities = GetCitiesUseCase().execute()
        Log.d(TAG, cities.toString())

        val weathers = GetCachedWeatherUseCase(cities).execute()
//        currentWeather.value = weathers

        try {
            currentWeather.value = GetCurrentWeatherUseCase("Kyiv").execute()
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            currentWeatherError.value = e.message
        }

    }
}
