package com.superman.movieticket.ui.profile.control;

import androidx.lifecycle.ViewModel
import com.superman.movieticket.domain.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileScreenViewModel : ViewModel(){
    private val _user:MutableStateFlow<User>?=null

    val user: MutableStateFlow<User>? get()=_user

}
