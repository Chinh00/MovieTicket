package com.superman.movieticket.infrastructure.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {
    val IS_AUTHENTICATE = stringPreferencesKey("IS_AUTHENTICATE")
    val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    val IS_PUSH_TOKEN_NOTI = stringPreferencesKey("IS_PUSH_TOKEN_NOTI")
}