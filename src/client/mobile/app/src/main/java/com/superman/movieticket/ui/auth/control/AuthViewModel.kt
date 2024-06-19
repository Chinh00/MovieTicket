package com.superman.movieticket.ui.auth.control

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(application:Application): AndroidViewModel(application) {
    lateinit var googleSignInClient: GoogleSignInClient
     val secondaryAuth:FirebaseAuth
    init{
        val optionsSecondary = FirebaseOptions.Builder()
            .setProjectId("movie-ticket-mobile")
            .setApplicationId("1:102542804094:android:f689ea14dd4f359c97042f")
            .setApiKey("AIzaSyBheLL2QWpWpUGbdx2JicJVRs5D34MtHhs")

            .build()
        if (FirebaseApp.getApps(application).none { it.name == "main" }) {
            FirebaseApp.initializeApp(application, optionsSecondary, "main")
        }

        // Lấy instance của FirebaseAuth từ dự án phụ
        val secondaryApp = FirebaseApp.getInstance("main")
        secondaryAuth = FirebaseAuth.getInstance(secondaryApp)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("102542804094-95u4rbniifbk4pa4mjuqsnnd30oedg77.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
    }
    fun signInWithGoogle(task: Task<GoogleSignInAccount>, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    if (account != null) {
                        Log.d("GoogleSignIn", "Account ID Token: ${account.idToken}")
                        firebaseAuthWithGoogle(account.idToken!!, onSuccess)
                    } else {
                        Log.d("GoogleSignIn", "GoogleSignInAccount is null")
                        onSuccess(false)
                    }
                } catch (e: ApiException) {
                    Log.e("GoogleSignIn", "signInResult:failed code=${e.statusCode}, message=${e.message}")
                    onSuccess(false)
                }
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