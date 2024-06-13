package com.superman.movieticket.ui.auth.control

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginSocialViewModel: ViewModel() {
    lateinit var googleSignInClient: GoogleSignInClient

    fun configureGoogleSignIn(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun handleSignInGoogleResult(task: Task<GoogleSignInAccount>, onSuccess: (GoogleSignInAccount) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Log thông tin chi tiết tài khoản
//            Log.d("SignInViewModel", "Google Sign-In successful")
//            Log.d("SignInViewModel", "Display Name: ${account.displayName}")
//            Log.d("SignInViewModel", "Email: ${account.email}")
//            Log.d("SignInViewModel", "Given Name: ${account.givenName}")
//            Log.d("SignInViewModel", "Family Name: ${account.familyName}")
//            Log.d("SignInViewModel", "ID: ${account.id}")
//            Log.d("SignInViewModel", "ID Token: ${account.idToken}")
            onSuccess(account)
        } catch (e: ApiException) {
            Log.w("SignInViewModel", "signInResult:failed code=" + e.statusCode)
            onFailure(e)
        }
    }
    fun handleSignFacebookResult(task: Task<LoginResult>, onSuccess: (LoginResult) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Log thông tin chi tiết tài khoản
//            Log.d("SignInViewModel", "Google Sign-In successful")
//            Log.d("SignInViewModel", "Display Name: ${account.displayName}")
//            Log.d("SignInViewModel", "Email: ${account.email}")
//            Log.d("SignInViewModel", "Given Name: ${account.givenName}")
//            Log.d("SignInViewModel", "Family Name: ${account.familyName}")
//            Log.d("SignInViewModel", "ID: ${account.id}")
//            Log.d("SignInViewModel", "ID Token: ${account.idToken}")
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