package com.superman.movieticket.ui.auth.control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginFaceBookViewModel: ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    val callbackManager: CallbackManager = CallbackManager.Factory.create()



    fun registerFacebookCallback() {
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                result?.accessToken?.let { token ->
                    _loginState.value = LoginState.Loading
                    authenticateWithBackend(token)
                }
            }

            override fun onCancel() {
                _loginState.value = LoginState.Error("Login canceled")
            }

            override fun onError(error: FacebookException) {
                _loginState.value = LoginState.Error(error?.message ?: "Unknown error")
            }
        })
    }

    private fun authenticateWithBackend(accessToken: AccessToken) {
        viewModelScope.launch {

        }
    }
}
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}