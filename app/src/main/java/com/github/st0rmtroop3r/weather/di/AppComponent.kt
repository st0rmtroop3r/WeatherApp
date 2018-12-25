package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}