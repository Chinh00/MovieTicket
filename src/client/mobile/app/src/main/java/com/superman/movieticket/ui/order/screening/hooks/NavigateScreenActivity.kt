package com.superman.movieticket.ui.order.screening.hooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.superman.movieticket.infrastructure.utils.saveStringToSharedPreferences
import com.superman.movieticket.ui.order.screening.ScreenActivity

fun NavigateScreenActivity(
    context: Context,
    movieId: String,
    image:String,

) {
    saveStringToSharedPreferences(context, "my_image", image)
    val intent = Intent(context, ScreenActivity::class.java)
    val bundle = Bundle()
    bundle.putString("movieId", movieId)
    intent.putExtras(bundle)
    context.startActivity(intent)
}