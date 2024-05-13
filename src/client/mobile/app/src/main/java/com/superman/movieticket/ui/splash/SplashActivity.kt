package com.superman.movieticket.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.superman.movieticket.R
import com.superman.movieticket.core.view.ActivityBase
import com.superman.movieticket.databinding.ActivitySplashBinding
import com.superman.movieticket.ui.shared.activity.MainActivity
import com.superman.movieticket.ui.splash.control.SplashActivityModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ActivityBase<ActivitySplashBinding, SplashActivityModel>() {



    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun getViewModel() = SplashActivityModel::class.java

    override fun setupViews() {
        supportActionBar?.hide()
    }

    override fun setupActions() {
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(2000L)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }


    }

}