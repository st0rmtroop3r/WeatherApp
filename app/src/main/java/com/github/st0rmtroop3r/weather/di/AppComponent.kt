package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.view.MainFragment
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import com.github.st0rmtroop3r.weather.viewmodel.usecase.AddCityUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCachedWeatherUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCitiesUseCase
import com.github.st0rmtroop3r.weather.viewmodel.usecase.GetCurrentWeatherUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    DbRepository::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
    fun inject(fragment: MainFragment)
    fun inject(useCase: GetCitiesUseCase)
    fun inject(useCase: GetCurrentWeatherUseCase)
    fun inject(useCase: GetCachedWeatherUseCase)
    fun inject(useCase: AddCityUseCase)
}