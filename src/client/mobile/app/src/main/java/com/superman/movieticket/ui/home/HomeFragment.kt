package com.superman.movieticket.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.superman.movieticket.core.view.FragmentBase
import com.superman.movieticket.databinding.FragmentHomeBinding
import com.superman.movieticket.ui.home.control.HomeFragmentModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : FragmentBase<FragmentHomeBinding, HomeFragmentModelImpl>() {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModel() = HomeFragmentModelImpl::class.java

    override fun setupViews() {

    }


    override fun onStart() {
        super.onStart()


    }

    override fun setupActions() {

    }

}