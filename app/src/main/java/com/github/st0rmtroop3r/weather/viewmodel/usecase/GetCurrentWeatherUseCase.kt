package com.github.st0rmtroop3r.weather.viewmodel.usecase

import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

open class GetCurrentWeatherUseCase
    @Inject constructor(
        private val weatherRepository: WeatherRepository,
        private val resources: Resources
    ) {

    fun execute(
        text: String,
        scope: CoroutineScope,
        onSuccess: (Weather) -> Unit,
        onError: (Exception) -> Unit
    ) {

        if (TextUtils.isEmpty(text)) {
            val message = resources.getString(R.string.city_search_input_error)
            onError.invoke(Exception(message))
            return
        }

        scope.launch(Dispatchers.IO) {
            try {
                val deferred = weatherRepository.requestWeatherAsync(text)
                deferred.await()
                deferred.getCompleted()
                    ?. also(onSuccess)
                    ?: throw NullPointerException("Response is NULL")
            } catch (e: Exception) {
                Log.e(TAG, "execute: ", e)
                onError.invoke(e)
            }
        }
    }

    companion object {
        val TAG = GetCurrentWeatherUseCase::class.java.simpleName
    }
}