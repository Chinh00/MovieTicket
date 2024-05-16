package com.superman.movieticket.infrastructure.di

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
    public fun RetrofitProvider() = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(ScalarsConverterFactory.create()).build()

}