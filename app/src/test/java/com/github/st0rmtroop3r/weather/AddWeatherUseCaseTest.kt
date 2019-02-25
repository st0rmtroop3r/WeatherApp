package com.github.st0rmtroop3r.weather

import com.github.st0rmtroop3r.weather.model.entities.Weather
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddWeatherUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddWeatherUseCaseTest {

    @Mock
    lateinit var weather: Weather

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @InjectMocks
    lateinit var addWeatherUseCase: AddWeatherUseCase

    @Test
    fun test_execution_with_non_null_param() {

        var result: Any? = Object()
        runBlocking {
            addWeatherUseCase.execute(weather, this) { result = it }
        }
        assert(result == null)
    }

    @Test
    fun test_execution_with_null_param() {

        var result: Any? = Object()
        runBlocking {
            addWeatherUseCase.execute(null, this) { result = it }
        }
        assert(result is NullPointerException)
    }

    @Test
    fun test_execution_with_non_null_param_without_callback() {

        runBlocking {
            addWeatherUseCase.execute(weather, this)
        }
        assert(true)
    }

    @Test
    fun test_execution_if_exception_occured_without_callback() {

        runBlocking {

            doThrow(UnsupportedOperationException()).`when`(weatherRepository).addWeather(weather)

            addWeatherUseCase.execute(weather, this)
        }
        assert(true)
    }
}

