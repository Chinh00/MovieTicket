package com.superman.movieticket.ui.auth.control

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.di.CoroutineScopeIO
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.PreferenceKey
import com.superman.movieticket.ui.auth.model.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginGoogleViewModel @Inject constructor(
    application: Application,
    private val authService: AuthService,
    private val dataStore: DataStore<Preferences>

): AndroidViewModel(application) {
    var googleSignInClient: GoogleSignInClient
    val secondaryAuth:FirebaseAuth

    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()


    init{
        val optionsSecondary = FirebaseOptions.Builder()
            .setProjectId("movie-ticket-mobile")
            .setApplicationId("1:102542804094:android:f689ea14dd4f359c97042f")
            .setApiKey("AIzaSyBheLL2QWpWpUGbdx2JicJVRs5D34MtHhs")

            .build()
        if (FirebaseApp.getApps(application).none { it.name == "main" }) {
            FirebaseApp.initializeApp(application, optionsSecondary, "main")
        }

        val secondaryApp = FirebaseApp.getInstance("main")
        secondaryAuth = FirebaseAuth.getInstance(secondaryApp)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AppOptions.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
    }


    fun signInWithGoogle(task: Task<GoogleSignInAccount>, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            _apiLoading.value = ApiState.LOADING
            val account = task.getResult(ApiException::class.java)!!

            if (task.isSuccessful) {
                Log.d("GoogleSignIn", "Account ID Token: ${account.idToken}")
                firebaseAuthWithGoogle(account.idToken!!, onSuccess)
                onSuccess(true)
                authService.getTokenSocial("external", "mobile", "api profile openid", account.idToken.toString(), "secret", "google").enqueue(object:
                    retrofit2.Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>,
                        response: Response<TokenResponse>
                    ) {
                        _apiLoading.value = ApiState.SUCCESS
                        viewModelScope.launch(Dispatchers.IO) {
                            dataStore.edit {
                                it[PreferenceKey.IS_AUTHENTICATE] = "true"
                                it[PreferenceKey.ACCESS_TOKEN] = response.body()?.access_token.toString()
                            }
                        }
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        Log.d(call.javaClass.name, t.toString())
                    }

                })
            } else {
                Log.d("GoogleSignIn", "GoogleSignInAccount is null")
                _apiLoading.value = ApiState.SUCCESS
                onSuccess(false)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String, onSuccess: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        secondaryAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(true)
            } else {
                task.exception?.let {
                    Log.e("FirebaseAuth", "signInWithCredential:failure", it)
                }
                onSuccess(false)
            }
        }
    }

    fun signOut(onSignOutComplete: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            onSignOutComplete()
        }
    }


}