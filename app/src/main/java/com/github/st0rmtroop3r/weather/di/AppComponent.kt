package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.WeatherApp
import com.github.st0rmtroop3r.weather.view.MainFragment
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddCityUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCachedWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCitiesUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
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

    fun inject(viewModel: MainViewModel)
    fun inject(fragment: MainFragment)
    fun inject(useCase: GetCitiesUseCase)
    fun inject(useCase: GetCurrentWeatherUseCase)
    fun inject(useCase: GetCachedWeatherUseCase)
    fun inject(useCase: AddCityUseCase)

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApp>()
}