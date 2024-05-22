package com.superman.movieticket.ui.news.control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentImpl @Inject constructor(): NewsFragmentModel, ViewModel() {




    fun GetPostList() = run {
        viewModelScope.launch {
        }
    }

}