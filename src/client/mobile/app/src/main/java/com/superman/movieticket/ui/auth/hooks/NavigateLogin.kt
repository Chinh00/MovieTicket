package com.superman.movieticket.ui.auth.hooks

import android.content.Context
import android.content.Intent
import com.superman.movieticket.ui.auth.LoginActivity

fun NavigateLogin(
    context: Context
) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}