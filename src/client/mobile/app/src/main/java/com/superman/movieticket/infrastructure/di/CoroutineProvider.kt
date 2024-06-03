package com.superman.movieticket.infrastructure.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineProvider {

    @Provides
    @Singleton
    fun AddCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @Provides
    @Singleton
    fun AddCoroutineScopeMain() = CoroutineScope(Dispatchers.Main)

    @Provides
    @Singleton
    @CoroutineScopeDefault
    fun AddCoroutineScopeDefault() = CoroutineScope(Dispatchers.Default)


}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeIO

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeMain

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineScopeDefault