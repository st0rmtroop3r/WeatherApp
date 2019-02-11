package com.github.st0rmtroop3r.weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetWeatherByLocationUseCase
import com.google.android.gms.common.api.ResolvableApiException
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class AddWeatherViewModel
    @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
        private val addWeatherUseCase: AddWeatherUseCase,
        private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase
    ) : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    val keyboardIsOpen = MutableLiveData<Boolean>()
    val searchResult = MutableLiveData<Weather?>()
    val searchError = MutableLiveData<String>()
    val addWeatherResult = MutableLiveData<String?>()
    val showProgressBar = MutableLiveData<Boolean>()
    val icon = MutableLiveData<RequestCreator?>()
    val requestLocationPermission = MutableLiveData<Boolean>()
    val requestLocationEnable = MutableLiveData<ResolvableApiException?>()
    val isLocationServiceAvailable = MutableLiveData<Boolean>()

    init {
        keyboardIsOpen.value = true
        showProgressBar.value = false
        searchResult.value = null
        requestLocationPermission.value = false
        isLocationServiceAvailable.value = getWeatherByLocationUseCase.isLocationServiceAvailable()
    }

    fun onSearchClicked(text: String) {
        showProgressBar.value = true
        getCurrentWeatherUseCase.execute(text, viewModelScope,
            { onSearchSuccess(it) }, { onSearchError(it) })
    }

    fun onAddClicked() {
        addWeatherUseCase.execute(searchResult.value, viewModelScope) { onAddWeatherResult(it) }
    }

    fun onLocationClick() {
        getWeatherByLocation()
    }

    fun onLocationSettingsResult(enabled: Boolean) {
        if (enabled) getWeatherByLocation()
    }

    fun onLocationPermissionResult(granted: Boolean) {
        requestLocationPermission.postValue(false)
        if (granted) getWeatherByLocation()
    }

    private fun getWeatherByLocation() {
        searchError.postValue("")
        getWeatherByLocationUseCase.execute(viewModelScope, { onSearchSuccess(it) }, { onLocationError(it) })
    }

    private fun onLocationError(exception: Exception) {
        try {
            throw exception
        } catch (e: NullPointerException) {
            Log.e(TAG, "onLocationError: ${e.message}")
        } catch (e: SecurityException) {
            Log.e(TAG, "onLocationError: ${e.message}")
            requestLocationPermission.postValue(true)
        } catch (e: ResolvableApiException) {
            Log.e(TAG, "onLocationError: ${e.message}")
            requestLocationEnable.postValue(e)
        }
        onSearchError(exception)
    }

    private fun onSearchSuccess(weather: Weather) {
        searchResult.postValue(weather)
        searchError.postValue("")
        keyboardIsOpen.postValue(false)
        showProgressBar.postValue(false)

        weather.conditions?.getOrNull(0)?.icon?.let {
            weatherRepository.getWeatherIcon(it)
        }.apply {
            icon.postValue(this)
        }
    }

    private fun onSearchError(exception: Exception) {
        searchResult.postValue(null)
        searchError.postValue(exception.message)
        keyboardIsOpen.postValue(true)
        showProgressBar.postValue(false)
    }

    private fun onAddWeatherResult(exception: Exception?) {
        addWeatherResult.postValue(exception?.message)
    }

    override fun onCleared() {
        viewModelJob.cancel()
        getWeatherByLocationUseCase.stopLocationUpdates()
        super.onCleared()
    }

    companion object {
        val TAG = AddWeatherViewModel::class.java.simpleName
    }
}