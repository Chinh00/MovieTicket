package com.superman.movieticket.ui.auth.control

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.di.CoroutineScopeDefault
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.PreferenceKey
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
class LoginActivityModelImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<Preferences>,
    @CoroutineScopeDefault private val coroutineScope: CoroutineScope
) : ViewModel() {
    var apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.NONE)

    val _apiState: StateFlow<ApiState> = apiState.asStateFlow()


    suspend fun HandleLoginAction(userLoginModel: UserLoginModel) {
        viewModelScope.launch {
            apiState.value = ApiState.LOADING

            authService.getToken(grantType = "password", username = userLoginModel.username, password = userLoginModel.password, clientId = "react_client", scope = "openid profile api").enqueue(object: Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    apiState.value = ApiState.SUCCESS
                    coroutineScope.launch {
                        dataStore.edit {
                            it[PreferenceKey.IS_AUTHENTICATE] = "true"
                            it[PreferenceKey.ACCESS_TOKEN] = response.body()?.access_token.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    apiState.value = ApiState.FAIL
                    Log.d("Login", t.toString())
                }

            })

            apiState.value = ApiState.NONE
        }
    }


}