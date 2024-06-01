package com.superman.movieticket.infrastructure.di

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object SignalrProvider {
    @Provides
    @Singleton
    fun AddSignalR(): HubConnection = HubConnectionBuilder.create("ws://10.0.2.2:9000/room").build()

}