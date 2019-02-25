package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetWeatherByLocationUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val locationProviderClient: FusedLocationProviderClient?,
        private val locationSettingsClient: SettingsClient,
        private val locationRequest: LocationRequest,
        private val locationSettingsRequest: LocationSettingsRequest
    ) {

    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun execute(
        scope: CoroutineScope,
        onSuccess: (Weather) -> Unit,
        onError: (Exception) -> Unit
    ) {

        locationProviderClient?: let {
            onError.invoke(NullPointerException("FusedLocationProviderClient is null"))
            return
        }

        locationProviderClient.lastLocation
            .addOnSuccessListener {
                it ?. let { requestWeather(it, scope, onSuccess, onError) }
                    ?: let {
                        locationCallback = WeatherLocationCallback(scope, onSuccess, onError)
                        checkLocationSettings(onError)
                    }
            }.addOnFailureListener {
                onError.invoke(it)
            }

    }

    fun isLocationServiceAvailable() = locationProviderClient != null

    fun stopLocationUpdates() {
        locationCallback?.let {
            stopLocationUpdates(it)
        }
    }

    private fun checkLocationSettings(onError: (Exception) -> Unit) {

        locationSettingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                startLocationUpdates(locationCallback!!)
            }.addOnFailureListener {
                onError.invoke(it)
            }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(locationCallback: LocationCallback) {
        locationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates(locationCallback: LocationCallback) {
        locationProviderClient!!.removeLocationUpdates(locationCallback)
    }

    private fun requestWeather(
        location: Location,
        scope: CoroutineScope,
        onSuccess: (Weather) -> Unit,
        onError: ((Exception) -> Unit)?
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                val deferred = weatherRepository.requestWeatherAsync(location.latitude, location.longitude)
                deferred.await()
                val weather = deferred.getCompleted()
                weather?.let(onSuccess) ?: throw NullPointerException("Weather is null")
            } catch (e: Exception) {
                Log.e(TAG, "execute: ", e)
                onError?.invoke(e)
            }
        }
    }

    private inner class WeatherLocationCallback(
        private val scope: CoroutineScope,
        private val onSuccess: (Weather) -> Unit,
        private val onError: (Exception) -> Unit
    ) : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            stopLocationUpdates(this)
            requestWeather(locationResult.lastLocation, scope, onSuccess, onError)
        }
    }

    companion object {
        val TAG: String = GetWeatherByLocationUseCase::class.java.simpleName
    }
}