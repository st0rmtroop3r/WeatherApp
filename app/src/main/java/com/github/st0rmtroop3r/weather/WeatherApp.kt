package com.github.st0rmtroop3r.weather

import android.app.Application
import android.content.Context
import com.github.st0rmtroop3r.weather.di.AppComponent
import com.github.st0rmtroop3r.weather.di.DaggerAppComponent

class WeatherApp : Application() {

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appComponent = DaggerAppComponent.create()
    }

}
