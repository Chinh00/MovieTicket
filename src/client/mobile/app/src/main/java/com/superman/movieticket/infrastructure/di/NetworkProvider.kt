package com.superman.movieticket.infrastructure.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.ScreeningService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {
    @Provides
    @Singleton
    fun gsonProvider(): Gson {
        return GsonBuilder().setLenient().create()
    }
    @Provides
    @Singleton
    fun RetrofitProvider() = Retrofit.Builder().baseUrl(AppOptions.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()







}