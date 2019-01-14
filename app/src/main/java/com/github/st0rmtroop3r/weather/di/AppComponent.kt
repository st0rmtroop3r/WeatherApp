package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.view.MainFragment
import com.github.st0rmtroop3r.weather.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(viewModel: MainViewModel)
    fun inject(fragment: MainFragment)
}