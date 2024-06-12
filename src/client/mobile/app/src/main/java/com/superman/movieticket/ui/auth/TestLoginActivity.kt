package com.superman.movieticket.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.superman.movieticket.ui.auth.control.LoginSocialViewModel
import kotlinx.coroutines.launch

class TestLoginActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
    }
    @Preview(showSystemUi = true)
    @Composable
    fun SignInScreen(viewModel: LoginSocialViewModel = viewModel()) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        viewModel.configureGoogleSignIn(context)

        val signInLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleSignInResult(task,
                onSuccess = { account ->
                    Log.d("account",account.toString())
                },
                onFailure = { exception ->
                    // Xử lý khi đăng nhập thất bại
                    Log.d("account",exception.message.toString())

                }
            )
        }

        Button(onClick = {
            val signInIntent = viewModel.googleSignInClient.signInIntent
            coroutineScope.launch {
                signInLauncher.launch(signInIntent)
            }
        }) {
            Text(text = "Sign in with Google")
        }
    }

}

