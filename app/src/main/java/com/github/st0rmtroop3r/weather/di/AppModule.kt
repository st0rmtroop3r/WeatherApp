package com.github.st0rmtroop3r.weather.di

import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.model.repository.WeatherRepository
import com.github.st0rmtroop3r.weather.view.adapter.CurrentWeatherRecyclerAdapter
import com.google.android.gms.location.*
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
        picasso.isLoggingEnabled = false
        return picasso
    }

    @Provides
    fun provideCurrentWeatherRecyclerAdapter(
        weatherRepository: WeatherRepository,
        resources: Resources
    ): CurrentWeatherRecyclerAdapter {
        return CurrentWeatherRecyclerAdapter(weatherRepository, resources)
    }

    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient? {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    fun provideLocationSettingsClient(context: Context): SettingsClient {
        return LocationServices.getSettingsClient(context)
    }

    @Provides
    fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 0
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    @Provides
    fun provideLocationSettingsRequest(locationRequest: LocationRequest): LocationSettingsRequest {
        return LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
    }
}