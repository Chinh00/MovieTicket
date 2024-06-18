package com.superman.movieticket.ui.auth.control

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.superman.movieticket.R
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.di.CoroutineScopeDefault
import com.superman.movieticket.infrastructure.di.CoroutineScopeIO
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.PreferenceKey
import com.superman.movieticket.ui.auth.model.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginSocialViewModel @Inject constructor(
    private val authService: AuthService,
    @CoroutineScopeDefault private val coroutineScope: CoroutineScope,
    val dataStore: DataStore<Preferences>,
) : ViewModel() {
    lateinit var googleSignInClient: GoogleSignInClient

    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()

    val isLogin = dataStore.data.map { it[PreferenceKey.IS_AUTHENTICATE]
    }


    init {
    }



    fun configureGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("759290814660-t25235hlv3t765rph6qh897q2q4f7dn9.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun handleSignInGoogleResult(
        task: Task<GoogleSignInAccount>,
        onSuccess: (GoogleSignInAccount) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val account = task.getResult(ApiException::class.java)
            viewModelScope.launch {
                _apiLoading.value = ApiState.LOADING
                onSuccess(account)
                account?.idToken?.let { Log.d("Chinh id Token", it) }
                authService.getTokenSocial(clientId = "mobile", token = account.idToken.toString(), scope = "api profile openid", grantType = "external", secret = "secret", provider = "google").enqueue(object:
                    retrofit2.Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>,
                        response: Response<TokenResponse>
                    ) {
                        coroutineScope.launch {
                            dataStore.edit {
                                it[PreferenceKey.IS_AUTHENTICATE] = "true"
                                it[PreferenceKey.ACCESS_TOKEN] = response.body()?.access_token.toString()
                            }
                        }
                        Log.d("Chinh acccess token", response.body()?.access_token.toString())
                        _apiLoading.value = ApiState.SUCCESS
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        Log.d(
                            "Chinh",
                            t.toString()
                        )
                        _apiLoading.value = ApiState.FAIL

                    }

                })
            }



        } catch (e: ApiException) {
            Log.w("SignInViewModel", "signInResult:failed code=" + e.toString())
            onFailure(e)
        }
    }



    fun signOut(onSignOutComplete: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            onSignOutComplete()
        }
    }

    fun handleSignFacebookResult(
        task: Task<LoginResult>,
        onSuccess: (LoginResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.d("SignInViewModel", "Google Sign-In successful")
            onSuccess(account)
        } catch (e: ApiException) {
            Log.w("SignInViewModel", "signInResult:failed code=" + e.statusCode)
            onFailure(e)
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val name: String, val email: String) : LoginState()
    data class Error(val message: String) : LoginState()
}