package com.superman.movieticket.ui.shared.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.superman.movieticket.R
import com.superman.movieticket.core.view.ActivityBase
import com.superman.movieticket.databinding.ActivityMainBinding
import com.superman.movieticket.ui.home.HomeFragment
import com.superman.movieticket.ui.news.NewsFragment
import com.superman.movieticket.ui.shared.activity.control.MainActivityModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ActivityBase<ActivityMainBinding, MainActivityModel>() {
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel(): Class<MainActivityModel> = MainActivityModel::class.java

    override fun setupViews() {
        switchFragment(HomeFragment())
    }

    override fun setupActions() {
        _binding.bottomBar.apply {
            setOnItemSelectedListener { t -> when(t) {
                0 -> {
                    switchFragment(HomeFragment())
                }
                1 -> {
                    switchFragment(NewsFragment())
                }
            } }
        }


    }
    @SuppressLint("CommitTransaction")
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations( com.google.android.material.R.anim.abc_slide_in_bottom,
            com.google.android.material.R.anim.abc_fade_out, // exit
            com.google.android.material.R.anim.abc_fade_in, // popEnter
            com.google.android.material.R.anim.abc_slide_out_top );
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }



}