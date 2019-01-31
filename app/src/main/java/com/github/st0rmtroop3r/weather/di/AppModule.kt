package com.github.st0rmtroop3r.weather.di

import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.view.adapter.CurrentWeatherRecyclerAdapter
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideAppContext(): Context {
        return WeatherApp.appContext
    }

    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Provides
    fun provideInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    fun providePicasso(): Picasso {
        val picasso = Picasso.get()
        picasso.isLoggingEnabled = true
        return picasso
    }

    @Provides
    fun provideCurrentWeatherRecyclerAdapter(
        weatherRepository: WeatherRepository,
        resources: Resources
    ): CurrentWeatherRecyclerAdapter {
        return CurrentWeatherRecyclerAdapter(weatherRepository, resources)
    }
}