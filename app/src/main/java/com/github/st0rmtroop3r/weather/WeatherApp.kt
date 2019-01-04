package com.github.st0rmtroop3r.weather

import android.app.Application
import com.github.st0rmtroop3r.weather.di.AppComponent
import com.github.st0rmtroop3r.weather.di.DaggerAppComponent

class WeatherApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

}
