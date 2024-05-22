package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.domain.services.TestService
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
    fun RetrofitProvider() = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(ScalarsConverterFactory.create()).build()


    @Provides
    @Singleton
    fun AddTestService(retrofit: Retrofit) = retrofit.create(TestService::class.java)
}