package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.core.config.AppOptions
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
    fun RetrofitProvider() = Retrofit.Builder().baseUrl(AppOptions.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build()





}