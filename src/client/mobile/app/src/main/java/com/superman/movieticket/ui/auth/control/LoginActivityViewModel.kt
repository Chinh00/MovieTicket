package com.superman.movieticket.ui.auth.control

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.services.AuthService
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.infrastructure.utils.PreferenceKey
import com.superman.movieticket.ui.auth.model.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<Preferences>,
) : ViewModel () {
    public lateinit var googleSignInClient: GoogleSignInClient
    public lateinit var secondaryAuth: FirebaseAuth

    val callbackManager: CallbackManager = CallbackManager.Factory.create()


    val _apiLoading = MutableStateFlow(ApiState.NONE)
    val apiLoading = _apiLoading.asStateFlow()

    val isLogin = dataStore.data.map { it[PreferenceKey.IS_AUTHENTICATE]
    }




    private val _verificationId = MutableStateFlow("")
    val verificationId: StateFlow<String> get() = _verificationId
    fun setVerifyCode(code: String) {
        _verificationId.value = code
    }
    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> get() = _timeLeft
    private val _otpValue = MutableStateFlow("")




    init {

    }







    init {
        registerFacebookCallback()
    }



    public fun InitConfigLoginGoogle (context: Context) {
        val optionsSecondary = FirebaseOptions.Builder()
            .setProjectId("movie-ticket-mobile")
            .setApplicationId("1:102542804094:android:f689ea14dd4f359c97042f")
            .setApiKey("AIzaSyBheLL2QWpWpUGbdx2JicJVRs5D34MtHhs")
            .build()
        if (FirebaseApp.getApps(context).none { it.name == "main" }) {
            FirebaseApp.initializeApp(context, optionsSecondary, "main")
        }

        val secondaryApp = FirebaseApp.getInstance("main")
        secondaryAuth = FirebaseAuth.getInstance(secondaryApp)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AppOptions.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context.applicationContext, gso)
    }



    public fun signInWithGoogle(task: Task<GoogleSignInAccount>, onSuccess: (Boolean) -> Unit) {
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



    private fun registerFacebookCallback() {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    result?.accessToken?.let { token ->
                        fetchFacebookUserProfile(token)
                        handleFacebookAccessToken(token,secondaryAuth, onLoginSuccess={
                            Log.d("FaceBookSignIn", "Firebase : ${it?.email}")

                        } , onLoginError={
                            Log.d("FaceBookSignIn", "Firebase : ${it.message}")
                        })
                        Log.d("FaceBookSignIn", "User token: ${result.accessToken.token}")

                    }
                    _apiLoading.value = ApiState.SUCCESS
                }

                override fun onCancel() {
                    _apiLoading.value = ApiState.FAIL
                }

                override fun onError(error: FacebookException) {
                    _apiLoading.value = ApiState.FAIL
                    Log.e("FaceBookSignIn", "Firebase user email: ${error?.message}")

                }
            })
    }

    private fun fetchFacebookUserProfile(accessToken: AccessToken) {
        viewModelScope.launch {
            try {
                val request = GraphRequest.newMeRequest(
                    accessToken
                ) { jsonObject, _ ->
                    val name = jsonObject!!.getString("name")
                    val email = jsonObject!!.getString("email")
                    Log.d("FaceBookSignIn",email)
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")
                request.parameters = parameters
                request.executeAsync()
            } catch (e: Exception) {
            }
        }
    }
    private fun handleFacebookAccessToken(
        token: AccessToken,
        firebaseAuth: FirebaseAuth,
        onLoginSuccess: (FirebaseUser?) -> Unit,
        onLoginError: (Exception) -> Unit
    ) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    if (user != null) {
                        Log.d("FaceBookSignIn", "Firebase user email: ${user.email}")
                        onLoginSuccess(user)
                    } else {
                        onLoginError(Exception("User is null"))
                    }
                } else {
                    Log.d("FaceBookSignIn", "Error signing in with credential: ${task.exception?.message}")
                    onLoginError(task.exception ?: Exception("Unknown error"))
                }
            }
    }




    fun setOtpValue(otp: String) {
        _otpValue.value = otp
    }



    fun sendOtp(phoneNumber: String, activity: Activity, onOtpSent: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                            onOtpSent(true, "")
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            e.printStackTrace()
                            onOtpSent(false, e.message ?: "Unknown error")
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            this@LoginActivityViewModel._verificationId.value = verificationId
                            onOtpSent(true, verificationId)
                        }
                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            } catch (e: Exception) {
                e.printStackTrace()
                onOtpSent(false, e.message ?: "Unknown error")
            }
        }
    }



    fun verifyOtp(otp: String, onVerified: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val credential = PhoneAuthProvider.getCredential(verificationId.value, otp)
                signInWithPhoneAuthCredential(credential) { success ->
                    onVerified(success)
                    getAccessToken{
                        onVerified(success)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onVerified(false)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, onSuccess: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        onSuccess(true)
                    } else {
                        task.exception?.printStackTrace()
                        onSuccess(false)
                    }
                }
        }
    }




    private fun getAccessToken(onTokenReceived: (String?) -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.getIdToken(true)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result?.token
                    Log.d("Acctoken for mobile", token!!)
                    authService.getTokenSocial("firebase_token", "mobile", "api profile openid", token!!, "secret", "firebase").enqueue(object:
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
                    onTokenReceived(token)
                } else {
                    onTokenReceived(null)
                }
            }
    }


}