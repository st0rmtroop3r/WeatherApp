package com.github.st0rmtroop3r.weather.model.repository

import android.net.Uri
import com.github.st0rmtroop3r.weather.model.db.WeatherDao
import com.github.st0rmtroop3r.weather.model.entities.City
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository

    @Inject
    constructor(
        private val weatherDao: WeatherDao,
        private val openWeatherMapApi: OpenWeatherMapApi,
        private val picasso: Picasso
    ) {

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

    suspend fun deleteWeather(weather: Weather) {
        withContext(Dispatchers.IO) {
            weatherDao.delete(weather)
        }
    }

    suspend fun updateCurrentWeather(citiesIds: String) {
        val weatherList = requestCurrentWeather(citiesIds)
        weatherDao.saveWeatherList(weatherList)
    }

    fun getWeatherIcon(icon: String): RequestCreator? {
        openWeatherMapApi
        val url = OpenWeatherMapApi.iconUrl(icon)
        val uri = Uri.parse(url)
        return picasso.load(uri)
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