package com.github.st0rmtroop3r.weather.di

import com.github.st0rmtroop3r.weather.model.network.OpenWeatherMapApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger2 module that provides network-related classes
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOpenWeatherMapApi(retrofit: Retrofit): OpenWeatherMapApi {
        return retrofit.create(OpenWeatherMapApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OpenWeatherMapApi.baseUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}