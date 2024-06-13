package com.superman.movieticket.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.facebook.AccessToken



class ScreenLogOut :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken: AccessToken = AccessToken.getCurrentAccessToken()!!
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        setContent {
            if(isLoggedIn){
                Text(text = "name")

            }
        }
    }
}