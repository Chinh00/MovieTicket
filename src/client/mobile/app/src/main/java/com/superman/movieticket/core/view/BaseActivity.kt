package com.superman.movieticket.core.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

public abstract class BaseActivity<ViewModel: androidx.lifecycle.ViewModel> : ComponentActivity() {
    protected lateinit var _model: ViewModel

    public abstract fun getViewModel(): Class<ViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _model = ViewModelProvider(this)[getViewModel()]
    }
}