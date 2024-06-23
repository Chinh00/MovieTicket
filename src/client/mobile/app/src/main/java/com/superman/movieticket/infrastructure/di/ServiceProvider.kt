package com.superman.movieticket.infrastructure.di

import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.domain.services.MovieService
import com.superman.movieticket.domain.services.NotificationService
import com.superman.movieticket.domain.services.ReservationService
import com.superman.movieticket.domain.services.RoomService
import com.superman.movieticket.domain.services.ScreeningService
import com.superman.movieticket.domain.services.ServiceService
import com.superman.movieticket.domain.services.TransactionService
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


    @Provides
    @Singleton
    fun AddScreeningService(retrofit: Retrofit) = retrofit.create(ScreeningService::class.java)


    @Provides
    @Singleton
    fun AddRoomService(retrofit: Retrofit) = retrofit.create(RoomService::class.java)


    @Provides
    @Singleton
    fun AddServiceService(retrofit: Retrofit) = retrofit.create(ServiceService::class.java)

    @Provides
    @Singleton
    fun AddNotificationService(retrofit: Retrofit) = retrofit.create(NotificationService::class.java)

    @Provides
    @Singleton
    fun AddReservationService(retrofit: Retrofit) = retrofit.create(ReservationService::class.java)


    @Provides
    @Singleton
    fun AddTransactionService(retrofit: Retrofit) = retrofit.create(TransactionService::class.java)

}
