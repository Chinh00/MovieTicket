package com.superman.movieticket.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class FragmentBase<Binding: ViewBinding, ViewModel: androidx.lifecycle.ViewModel> : Fragment() {
    protected lateinit var _binding: Binding
    protected lateinit var _model: ViewModel

    protected abstract fun getViewBinding(): Binding
    protected abstract fun getViewModel(): Class<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        _model = ViewModelProvider(this)[getViewModel()]

        setupViews()
        setupActions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _binding.root
    }

    abstract fun setupViews()
    abstract fun setupActions()


}