package com.superman.movieticket.infrastructure.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.ScreeningService
import com.superman.movieticket.infrastructure.networks.SuspendInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {


    @Provides
    @Singleton
    fun okHttpClientProvider(
        dataStore: DataStore<Preferences>,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(SuspendInterceptor(dataStore))
            .callTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES).build()
    }
    @Provides
    @Singleton
    fun gsonProvider(): Gson {
        return GsonBuilder().setLenient().create()
    }
    @Provides
    @Singleton
    fun RetrofitProvider(okHttpClient: OkHttpClient) = Retrofit.Builder().client(okHttpClient).baseUrl(AppOptions.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()







}