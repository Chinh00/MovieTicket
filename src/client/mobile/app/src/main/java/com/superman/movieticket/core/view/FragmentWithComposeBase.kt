package com.superman.movieticket.core.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider

abstract class FragmentWithComposeBase<ViewModel: androidx.lifecycle.ViewModel> : Fragment() {
    protected lateinit var model: ViewModel
    protected abstract fun getViewModel(): Class<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[getViewModel()]
    }


}