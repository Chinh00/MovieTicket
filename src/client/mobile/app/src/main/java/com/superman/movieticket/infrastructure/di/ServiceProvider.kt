package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.domain.services.MovieService
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
    fun AddAuthService(retrofit: Retrofit) = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun AddMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

}
