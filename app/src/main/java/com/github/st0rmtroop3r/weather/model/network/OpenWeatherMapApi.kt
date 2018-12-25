package com.github.st0rmtroop3r.weather.model.network

import com.github.st0rmtroop3r.weather.BuildConfig
import com.github.st0rmtroop3r.weather.model.entities.WeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for https://openweathermap.org/ API
 */
interface OpenWeatherMapApi {

    companion object {
        const val baseUrl = "https://api.openweathermap.org/"
        const val versionPath = "data/2.5"
        const val apiKey = BuildConfig.OPEN_WEATHER_MAP_KEY
    }

    /**
     * Calls for current weather data for a given city name
     *
     * @param cityName city name and country code (optional) divided by comma, "London" or "London,uk"
     * @param (optional) OWM api key
     * @return WeatherResponse as coroutines Deferred
     */
    @GET( "/$versionPath/weather")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") appid: String = apiKey) : Deferred<WeatherResponse>

}