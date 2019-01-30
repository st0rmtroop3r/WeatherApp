package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.view.AddWeatherFragment
import com.github.st0rmtroop3r.weather.view.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment() : MainFragment

    @ContributesAndroidInjector
    abstract fun contributeAddWeatherFragment() : AddWeatherFragment
}