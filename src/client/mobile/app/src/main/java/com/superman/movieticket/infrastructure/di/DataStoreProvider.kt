package com.superman.movieticket.infrastructure.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreProvider {

    private const val AUTH_PREFERENCE_NAME = "auth"

    @Singleton
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_PREFERENCE_NAME)

    @Provides
    @Singleton
    fun AddDataStoreService (@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}