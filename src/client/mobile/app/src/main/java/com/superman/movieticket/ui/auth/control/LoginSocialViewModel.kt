package com.superman.movieticket.ui.auth.control

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
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

    fun handleSignInResult(task: Task<GoogleSignInAccount>, onSuccess: (GoogleSignInAccount) -> Unit, onFailure: (Exception) -> Unit) {
        try {
            val account = task.getResult(ApiException::class.java)
            // Log thông tin chi tiết tài khoản
            Log.d("SignInViewModel", "Google Sign-In successful")
            Log.d("SignInViewModel", "Display Name: ${account.displayName}")
            Log.d("SignInViewModel", "Email: ${account.email}")
            Log.d("SignInViewModel", "Given Name: ${account.givenName}")
            Log.d("SignInViewModel", "Family Name: ${account.familyName}")
            Log.d("SignInViewModel", "ID: ${account.id}")
            Log.d("SignInViewModel", "ID Token: ${account.idToken}")
            onSuccess(account)
        } catch (e: ApiException) {
            Log.w("SignInViewModel", "signInResult:failed code=" + e.statusCode)
            onFailure(e)
        }
    }
}