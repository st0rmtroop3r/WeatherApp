package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddCityUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.DeleteWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.SaveWeatherListOrderUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.UpdateCurrentWeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import javax.inject.Inject


class MainViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
        private val updateCurrentWeatherUseCase: UpdateCurrentWeatherUseCase,
        private val addCityUseCase: AddCityUseCase,
        private val deleteWeatherUseCase: DeleteWeatherUseCase,
        private val saveWeatherListOrderUseCase: SaveWeatherListOrderUseCase
    ): ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    val citiesList = weatherRepository.citiesLiveData()
    val currentWeather = weatherRepository.weatherList()
    val currentWeatherError = MutableLiveData<String>()
    val updateProgressIsVisible = MutableLiveData<Boolean>()

    init {
        addCityUseCase.execute(City(703448, "Kyiv"), viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onUpdateWeatherTriggered() {
        updateProgressIsVisible.value = true
        updateCurrentWeatherUseCase.execute(
            currentWeather.value,
            viewModelScope,
            { onUpdateSuccess() },
            { onUpdateError(it) })
    }

    fun onDeleteWeatherTriggered(weather: Weather, delay: Int) {
        Log.w(TAG, "onDeleteWeatherTriggered: ${weather.cityName}")
        deleteWeatherUseCase.execute(weather, viewModelScope, delay.toLong())
    }

    fun undoDelete(weather: Weather) {
        Log.w(TAG, "undoDelete: ${weather.cityName}")
        deleteWeatherUseCase.undoDelete(weather, viewModelScope)
    }

    private fun onUpdateSuccess() {
        updateProgressIsVisible.postValue(false)
    }

    private fun onUpdateError(e: Exception) {
        updateProgressIsVisible.postValue(false)
        currentWeatherError.postValue(e.message)
    }

    fun onWeatherListOrderChanged(weatherList: ArrayList<Weather>) {
        saveWeatherListOrderUseCase.execute(weatherList)
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}
