package com.github.st0rmtroop3r.weather.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.st0rmtroop3r.weather.R
import com.github.st0rmtroop3r.weather.di.DaggerAppComponent
import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    @Inject
    lateinit var api : OpenWeatherMapApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        DaggerAppComponent.create().inject(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        // test retrofit call to OWM
        val deferredWeather = api.getWeather("Kyiv")
        runBlocking {
            deferredWeather.await()
        }
        val weatherResponse = deferredWeather.getCompleted()
        Log.w(TAG, weatherResponse.toString())

    }

}
