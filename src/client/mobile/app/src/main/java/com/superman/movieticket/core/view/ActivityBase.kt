package com.superman.movieticket.core.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.superman.movieticket.R





abstract class ActivityBase<Binding: ViewBinding, ViewModel: androidx.lifecycle.ViewModel> : AppCompatActivity() {
    protected lateinit var _binding: Binding
    protected lateinit var _model: ViewModel

    protected abstract fun getViewBinding(): Binding
    protected abstract fun getViewModel(): Class<ViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        _model = ViewModelProvider(this)[getViewModel()]
        setContentView(_binding.root)

        setupViews()
        setupActions()

    }
    abstract fun setupViews()
    abstract fun setupActions()

}