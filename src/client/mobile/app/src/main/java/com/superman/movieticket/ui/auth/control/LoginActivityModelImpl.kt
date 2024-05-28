package com.superman.movieticket.ui.auth.control

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginActivityModelImpl @Inject constructor() : ViewModel(), LoginActivityModel {
    lateinit var username: MutableStateFlow<String>
    lateinit var password: MutableStateFlow<String>
    override val _username: MutableStateFlow<String>
        get() = username
    override val _password: MutableSharedFlow<String>
        get() = password


}