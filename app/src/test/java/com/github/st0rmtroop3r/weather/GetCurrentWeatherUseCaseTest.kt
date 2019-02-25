package com.github.st0rmtroop3r.weather

import android.content.res.Resources
import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCurrentWeatherUseCaseTest {

    @Mock
    lateinit var deferred: Deferred<Weather>

    @Mock
    lateinit var weather: Weather

    @Mock
    lateinit var resources: Resources

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @InjectMocks
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        `when`(resources.getString(ArgumentMatchers.anyInt())).thenReturn("Please type a city name")
        `when`(weatherRepository.requestWeatherAsync(anyString())).thenReturn(deferred)
        `when`(deferred.getCompleted()).thenReturn(weather)
    }

    @Test
    fun test_execution_with_non_empty_arg() {

        var result: Any? = Object()
        runBlocking {
            getCurrentWeatherUseCase.execute("smth", this, { result = it }, { result = it })
        }
        assert(result == weather)
    }

    @Test
    fun test_execution_with_empty_arg() {

        var result: Any? = Object()
        runBlocking {
            getCurrentWeatherUseCase.execute("", this, { result = it }, { result = it })
        }
        assert(result is IllegalArgumentException)
    }

    @Test
    fun test_execution_when_null_is_returned() {

        `when`(deferred.getCompleted()).thenReturn(null)

        var result: Any? = Object()
        runBlocking {
            getCurrentWeatherUseCase.execute("smth", this, { result = it }, { result = it })
        }
        assert(result is NullPointerException)
    }
}