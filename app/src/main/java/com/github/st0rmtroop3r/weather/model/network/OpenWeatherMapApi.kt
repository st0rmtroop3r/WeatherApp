package com.github.st0rmtroop3r.weather.model.network

import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.entities.WeatherList
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API Interface for https://openweathermap.org/
 */
interface OpenWeatherMapApi {

    /**
     * Calls for current weather data for a given city name
     *
     * @param cityName city name and country code (optional) separated by comma, "London" or "London,uk"
     * @param (optional) units format "metric" or "imperial". Default is metric
     * @return Weather as coroutines Deferred
     */
    @GET( "/$versionPath/weather")
    fun getWeatherAsync(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric"
    ) : Deferred<Weather?>

    /**
     * Calls for current weather data for several city IDs
     *
     * @param ids cities ids separated by comma, "1,2,3,4,5"
     * @param (optional) units format "metric" or "imperial". Default is metric
     * @return WeatherList as coroutines Deferred
     */
    @GET("/$versionPath/group")
    fun getWeatherListAsync(
        @Query("id") ids: String,
        @Query("units") units: String = "metric"
    ) : Deferred<WeatherList>

    companion object {

        const val baseUrl = "https://api.openweathermap.org/"
        const val versionPath = "data/2.5"

        fun iconUrl(icon: String): String {
            return "http://openweathermap.org/img/w/$icon.png"
        }
    }

}