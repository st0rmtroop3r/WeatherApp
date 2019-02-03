package com.github.st0rmtroop3r.weather.model.repository

import android.net.Uri
import com.github.st0rmtroop3r.weather.model.db.WeatherDao
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.DeletedWeather
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.*
import javax.inject.Inject

class WeatherRepository

    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val openWeatherMapApi: OpenWeatherMapApi,
        private val picasso: Picasso
    ) {

    private var deletionJob: Job

    init {
        deletionJob = postponeWeatherDeletion(0)
    }

    suspend fun addCity(city: City) {
        withContext(Dispatchers.IO) {
            weatherDao.addCity(city)
        }
    }

    fun citiesLiveData() = weatherDao.getCities()

    fun removeCity(city: City) = weatherDao.removeCity(city)

    fun addWeather(weather: Weather) = weatherDao.saveWeather(weather)

    fun requestWeatherAsync(cityName: String) = openWeatherMapApi.getWeatherAsync(cityName)

    fun weatherList() = weatherDao.getWeatherList()

    fun deleteWeatherDelayed(cityId: Long, delayMillis: Long) {
        scheduleDeletion(delayMillis)
        weatherDao.markForDeletion(DeletedWeather(cityId))
    }

    fun removeDeletionRecord(deletedWeather: DeletedWeather) = weatherDao.removeDeletionRecord(deletedWeather)

    suspend fun updateCurrentWeather(citiesIds: String) {
        val weatherList = requestCurrentWeather(citiesIds)
        weatherDao.saveWeatherList(weatherList)
    }

    fun getWeatherIcon(icon: String): RequestCreator? {
        val url = OpenWeatherMapApi.iconUrl(icon)
        val uri = Uri.parse(url)
        return picasso.load(uri)
    }

    private fun scheduleDeletion(delayMillis: Long) {
        if (deletionJob.isActive) deletionJob.cancel()
        deletionJob = postponeWeatherDeletion(delayMillis)
    }

    private fun postponeWeatherDeletion(delayMillis: Long) = CoroutineScope(Dispatchers.IO).launch {
        delay(delayMillis)
        weatherDao.deleteMarkedWeather()
    }

    private suspend fun requestCurrentWeather(citiesIds: String): List<Weather> {
        val deferredWeatherList = openWeatherMapApi.getWeatherListAsync(citiesIds)
        deferredWeatherList.await()
        return deferredWeatherList.getCompleted().list
    }

    companion object {
        val TAG = WeatherRepository::class.java.simpleName
    }

}