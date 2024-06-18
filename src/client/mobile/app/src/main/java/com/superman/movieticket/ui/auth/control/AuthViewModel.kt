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
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(application:Application): AndroidViewModel(application) {
    private var secondaryAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    init{
        val optionsSecondary = FirebaseOptions.Builder()
            .setProjectId("phonesms-a6c92")
            .setApplicationId("1:640150146280:android:618c89670dcb7947d40d5d")
            .setApiKey("AIzaSyA7Yn78uSmSAzOyA7N1kzNO4OX2LYk2S68")

            .build()
        if (FirebaseApp.getApps(application).none { it.name == "secondary" }) {
            FirebaseApp.initializeApp(application, optionsSecondary, "secondary")
        }

        // Lấy instance của FirebaseAuth từ dự án phụ
        val secondaryApp = FirebaseApp.getInstance("secondary")
        secondaryAuth = FirebaseAuth.getInstance(secondaryApp)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("640150146280-2v1qt3vfokpripp2ho6ean5bi2gmarof.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
    }
    fun signInWithGoogle(task: Task<GoogleSignInAccount>, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleSignIn",account.idToken!!)
                firebaseAuthWithGoogle(account.idToken!!, onSuccess)
            } catch (e: ApiException) {
                onSuccess(false)
                Log.d("GoogleSignIn",e.message.toString())

            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String, onSuccess: (Boolean) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        secondaryAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess(true)
            } else {
                onSuccess(false)
            }
        }
    }




}