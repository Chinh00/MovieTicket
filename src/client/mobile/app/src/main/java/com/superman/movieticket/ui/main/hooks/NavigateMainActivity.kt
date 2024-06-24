package com.superman.movieticket.ui.main.hooks

import android.content.Context
import android.content.Intent
import com.superman.movieticket.ui.main.MainActivity


fun NavigateMainActivity (
    context: Context
) {
    val intent = Intent(context.applicationContext, MainActivity::class.java)
    context.startActivity(intent)
}