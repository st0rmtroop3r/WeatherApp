package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.WeatherApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    AndroidSupportInjectionModule::class,
    FragmentsModule::class,
    ViewModelModule::class,
    DbModule::class])
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApp>()
}