package com.superman.movieticket.infrastructure.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.ScreeningService
import com.superman.movieticket.infrastructure.utils.SuspendInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    fun okHttpClientProvider(
        dataStore: DataStore<Preferences>,
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(SuspendInterceptor(dataStore)).build()
    }
    @Provides
    @Singleton
    fun RetrofitProvider(okHttpClient: OkHttpClient) = Retrofit.Builder().baseUrl(AppOptions.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()







}