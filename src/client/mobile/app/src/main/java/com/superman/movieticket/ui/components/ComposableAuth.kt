package com.superman.movieticket.ui.components

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import com.superman.movieticket.ui.auth.LoginActivity


// sử dụng để wrapper các composable muốn xác thực trước khi sử dụng
@Composable
fun ComposableAuth (
    content: @Composable () -> Unit,
    isAuthenticate: Boolean,
) {

    val context = LocalContext.current
    if (!isAuthenticate) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    } else {
        content()
    }
}