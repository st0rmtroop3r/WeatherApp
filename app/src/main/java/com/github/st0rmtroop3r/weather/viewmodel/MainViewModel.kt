package com.github.st0rmtroop3r.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddCityUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.UpdateCurrentWeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainViewModel
    @Inject
    constructor(
        private val weatherRepository: WeatherRepository,
        private val updateCurrentWeatherUseCase: UpdateCurrentWeatherUseCase,
        private val addCityUseCase: AddCityUseCase
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

    fun onUpdateWeatherTriggered() {
        updateProgressIsVisible.value = true
        updateCurrentWeatherUseCase.execute(
            currentWeather.value,
            viewModelScope,
            { onUpdateSuccess() },
            { onUpdateError(it) })
    }

    private fun onUpdateSuccess() {
        updateProgressIsVisible.postValue(false)
    }

    fun onUpdateError(e: Exception) {
        updateProgressIsVisible.postValue(false)
        currentWeatherError.postValue(e.message)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}
