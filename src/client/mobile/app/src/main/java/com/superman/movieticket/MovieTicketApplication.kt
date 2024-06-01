package com.superman.movieticket

import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.superman.movieticket.ui.systems.ErrorActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieTicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            val intent = Intent(this, ErrorActivity::class.java)
            val bundle = Bundle()
            bundle.putString("error", e.message)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}