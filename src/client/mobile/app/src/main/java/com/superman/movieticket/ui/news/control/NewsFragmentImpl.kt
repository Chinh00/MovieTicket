package com.superman.movieticket.ui.news.control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.services.TestService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentImpl @Inject constructor(
    private val testService: TestService
): NewsFragmentModel, ViewModel() {




    fun GetPostList() = run {
        viewModelScope.launch {
        }
    }

}