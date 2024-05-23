package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.core.config.AuthRetrofit
import com.superman.movieticket.core.config.BaseRetrofit
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.domain.services.RoomService
import com.superman.movieticket.domain.services.ScreeningService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object ServiceProvider {
    @Provides
    @Singleton
    fun AddScreeningService(@BaseRetrofit retrofit: Retrofit) = retrofit.create(ScreeningService::class.java)

    @Provides
    @Singleton
    fun AddRoomService(@BaseRetrofit retrofit: Retrofit) = retrofit.create(RoomService::class.java)






//    Authenticate
    @Provides
    @Singleton
    fun AddAuthService(@AuthRetrofit retrofit: Retrofit) = retrofit.create(AuthService::class.java)

}
