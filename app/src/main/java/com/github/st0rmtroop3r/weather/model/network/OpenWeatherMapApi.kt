package com.github.st0rmtroop3r.weather.model.network

import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.entities.WeatherList
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
    }

    /**
     * Calls for current weather data for a given city name
     *
     * @param cityName city name and country code (optional) divided by comma, "London" or "London,uk"
     * @param (optional) OWM api key
     * @return WeatherResponse as coroutines Deferred
     */
    @GET( "/$versionPath/weather")
    fun getWeather(@Query("q") cityName: String) : Deferred<Weather>

    /**
     * Call for current weather data for several city IDs
     */
    @GET("/$versionPath/group")
    fun getWeathers(@Query("id") ids: String) : Deferred<WeatherList>

}