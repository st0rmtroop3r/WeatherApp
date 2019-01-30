package com.github.st0rmtroop3r.weather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class AddWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val addWeatherUseCase: AddWeatherUseCase
) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    val keyboardIsOpen = MutableLiveData<Boolean>()
    val result = MutableLiveData<Weather>()
    val resultIsVisible = MutableLiveData<Boolean>()
    val resultError = MutableLiveData<String>()
    val resultErrorIsVisible = MutableLiveData<Boolean>()
    val showProgressBar = MutableLiveData<Boolean>()
    val icon = MutableLiveData<RequestCreator?>()

    init {
        keyboardIsOpen.value = true
        resultIsVisible.value = false
        resultErrorIsVisible.value = false
        showProgressBar.value = false
    }

    fun onSearchClicked(text: String) {
        showProgressBar.value = true
        getCurrentWeatherUseCase.execute(text, viewModelScope,
            { onSearchSuccess(it) }, { onSearchError(it) })
    }

    fun onAddClicked() {
        addWeatherUseCase.execute(result.value, viewModelScope, onError = { onAddError(it) })
    }

    private fun onSearchSuccess(weather: Weather) {
        result.postValue(weather)
        resultIsVisible.postValue(true)
        resultError.postValue("")
        resultErrorIsVisible.postValue(false)
        keyboardIsOpen.postValue(false)
        showProgressBar.postValue(false)

        weather.conditions?.getOrNull(0)?.icon?.let {
            weatherRepository.getWeatherIcon(it)
        }.apply {
            icon.postValue(this)
        }
    }

    private fun onSearchError(exception: Exception) {
        result.postValue(null)
        resultIsVisible.postValue(false)
        resultError.postValue(exception.message)
        resultErrorIsVisible.postValue(true)
        keyboardIsOpen.postValue(true)
        showProgressBar.postValue(false)
    }

    private fun onAddError(exception: Exception) {
        resultError.postValue(exception.message)
        resultErrorIsVisible.postValue(true)
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    companion object {
        val TAG = AddWeatherViewModel::class.java.simpleName
    }
}