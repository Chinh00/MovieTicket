package com.superman.movieticket.ui.auth.control

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.model.TokenResponse
import com.superman.movieticket.ui.auth.model.UserLoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginActivityModelImpl @Inject constructor(private val authService: AuthService) : ViewModel(), LoginActivityModel {
    lateinit var username: MutableStateFlow<String>
    lateinit var password: MutableStateFlow<String>
    var apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.NONE)
    override val _username: MutableStateFlow<String>
        get() = username
    override val _password: MutableSharedFlow<String>
        get() = password
    val _apiState: StateFlow<ApiState> = apiState.asStateFlow()


    override suspend fun HandleLoginAction(userLoginModel: UserLoginModel) {

        viewModelScope.launch {
            apiState.emit(ApiState.LOADING)

            authService.getToken(grantType = "password", username = userLoginModel.username, password = userLoginModel.password, clientId = "react_client", scope = "openid profile api").enqueue(object: Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    apiState.value = ApiState.SUCCESS
                    Log.d("Login", response.toString())
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    apiState.value = ApiState.FAIL
                    Log.d("Login", t.message.toString())

                }

            })

            apiState.emit(ApiState.NONE)
        }
    }


}