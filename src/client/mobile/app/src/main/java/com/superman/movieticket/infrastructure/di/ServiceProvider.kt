package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.domain.services.ScreeningService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
<<<<<<< HEAD
object ServiceProvider
=======
object ServiceProvider {
    @Provides
    @Singleton
    fun AddScreeningService(retrofit: Retrofit) = retrofit.create(ScreeningService::class.java)

}
>>>>>>> 644d0811f51bf486f7675193a353940b88ac2a2c
