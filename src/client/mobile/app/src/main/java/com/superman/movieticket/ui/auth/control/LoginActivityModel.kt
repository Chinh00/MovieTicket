package com.superman.movieticket.ui.auth.control

import androidx.compose.runtime.MutableState
import com.superman.movieticket.ui.auth.model.UserLoginModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LoginActivityModel {
    public val _username: MutableStateFlow<String>
    public val _password: MutableSharedFlow<String>

    suspend fun HandleLoginAction(userLoginModel: UserLoginModel)

}