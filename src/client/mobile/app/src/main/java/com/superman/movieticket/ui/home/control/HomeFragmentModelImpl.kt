package com.superman.movieticket.ui.home.control

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeFragmentModelImpl @Inject constructor() : HomeFragmentModel, ViewModel()