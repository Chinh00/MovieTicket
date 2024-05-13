package com.superman.movieticket.ui.home

import com.superman.movieticket.core.view.FragmentBase
import com.superman.movieticket.databinding.FragmentHomeBinding
import com.superman.movieticket.ui.home.control.HomeFragmentModelImpl
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : FragmentBase<FragmentHomeBinding, HomeFragmentModelImpl>() {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModel() = HomeFragmentModelImpl::class.java

    override fun setupViews() {
    }

    override fun setupActions() {
    }

}