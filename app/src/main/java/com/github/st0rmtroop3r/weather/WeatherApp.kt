package com.github.st0rmtroop3r.weather

import android.content.Context
import com.github.st0rmtroop3r.weather.di.AppComponent
import com.github.st0rmtroop3r.weather.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

class WeatherApp : DaggerApplication() {

    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    override fun applicationInjector(): AppComponent {
        appComponent = DaggerAppComponent.builder().create(this) as AppComponent
        return appComponent
    }

}
