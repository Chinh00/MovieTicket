package com.superman.movieticket.ui.auth.control

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginFaceBookViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> get() = _loginState

    val callbackManager: CallbackManager = CallbackManager.Factory.create()

    init {
        registerFacebookCallback()
    }

    private fun registerFacebookCallback() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    result?.accessToken?.let { token ->
                        fetchFacebookUserProfile(token)
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

    private fun fetchFacebookUserProfile(accessToken: AccessToken) {
        viewModelScope.launch {
            // Use Facebook Graph API to get user profile
            try {
                val request = GraphRequest.newMeRequest(
                    accessToken
                ) { jsonObject, _ ->
                    val name = jsonObject!!.getString("name")
                    val email = jsonObject!!.getString("email")
                    Log.d("e",email)
                    _loginState.value = LoginState.Success(name, email)
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")
                request.parameters = parameters
                request.executeAsync()
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
