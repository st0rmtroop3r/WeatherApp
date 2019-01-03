package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.view_model.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
}