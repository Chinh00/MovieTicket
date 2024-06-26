package com.superman.movieticket

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.NotificationDeviceCreateModel
import com.superman.movieticket.domain.services.NotificationService
import com.superman.movieticket.ui.systems.ErrorActivity
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.ZoneId
import java.util.TimeZone

@HiltAndroidApp
class MovieTicketApplication : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            val intent = Intent(this, ErrorActivity::class.java)
            val bundle = Bundle()
            Log.d("error", e.toString())
            bundle.putString("error", e.toString())
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault()))
    }



}