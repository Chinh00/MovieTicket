package com.superman.movieticket.infrastructure.utils

import android.content.Context
import android.content.SharedPreferences

fun saveStringToSharedPreferences(context: Context, key: String, value: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}
fun getStringFromSharedPreferences(context: Context, key: String): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, null)
}