package com.superman.movieticket

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.superman.movieticket.ui.systems.ErrorActivity
import dagger.hilt.android.HiltAndroidApp
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
            bundle.putString("error", e.message)
            intent.putExtras(bundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.systemDefault()))
    }
}