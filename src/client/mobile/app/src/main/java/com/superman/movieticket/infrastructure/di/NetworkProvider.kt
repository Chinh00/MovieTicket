package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.core.config.AuthRetrofit
import com.superman.movieticket.core.config.BaseRetrofit
import com.superman.movieticket.domain.services.ScreeningService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {
    @Provides
    @Singleton
    @BaseRetrofit
    fun RetrofitProvider() = Retrofit.Builder().baseUrl(AppOptions.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build()


    @Provides
    @Singleton
    @AuthRetrofit
    fun AddHtppAuth() = Retrofit.Builder().baseUrl(AppOptions.AUTH_URL).addConverterFactory(ScalarsConverterFactory.create()).build()





}