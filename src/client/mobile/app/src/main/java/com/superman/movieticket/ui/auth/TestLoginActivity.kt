package com.superman.movieticket.ui.auth

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.superman.movieticket.R
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.theme.CustomBlue
import com.superman.movieticket.ui.theme.balooFont
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestLoginActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}

//@Preview(showSystemUi = true)
@Composable
fun SignInGoogleScreen() {
    val loginActivityViewModel: LoginActivityViewModel = hiltViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()



    Button(
        onClick = {
            loginActivityViewModel.signOut {

                val signInIntent = loginActivityViewModel.googleSignInClient.signInIntent
                Log.d("Firebase", loginActivityViewModel.secondaryAuth.toString())
                coroutineScope.launch {
                    (context as LoginActivity).startActivityForResult(
                        signInIntent,
                        LoginActivity.RC_SIGN_IN
                    )


                }
            }
        }, modifier = Modifier
            .height(50.dp)
            .shadow(
                shape = MaterialTheme.shapes.small,
                ambientColor = Color.Gray,
                elevation = 8.dp
            ), shape = MaterialTheme.shapes.small, colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )

    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
    }

}

@Composable
fun SignInFacebookComp(onLoginSuccess: (FirebaseUser?) -> Unit, onLoginError: (Exception) -> Unit) {
    val context = LocalContext.current
    val callbackManager = remember { CallbackManager.Factory.create() }
    val firebaseAuth = FirebaseAuth.getInstance()
    val loginActivityViewModel = hiltViewModel<LoginActivityViewModel>()

    // Register the callback only once
//    val loginManager = LoginManager.getInstance()
//    loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//        override fun onSuccess(result: LoginResult) {
//            Log.d("FaceBookSignIn", "Facebook access token: ${result.accessToken.token}")
//
//            handleFacebookAccessToken(
//                result.accessToken,
//                firebaseAuth,
//                onLoginSuccess = {
//                    Log.d("FaceBookSignIn", "Firebase user email: ${it?.email}")
//                    onLoginSuccess(it)
//                },
//                onLoginError = {
//                    Log.d("FaceBookSignIn", "Error: ${it.message}")
//                    onLoginError(it)
//                }
//            )
//        }
//
//        override fun onCancel() {
//            Log.d("FaceBookSignIn", "Facebook login canceled")
//        }
//
//        override fun onError(error: FacebookException) {
//            Log.d("FaceBookSignIn", "Facebook login error: ${error.message}")
//            onLoginError(error)
//        }
//    })


    Button(
        onClick = {
            LoginManager.getInstance().logOut()
            LoginManager.getInstance()
                .logInWithReadPermissions(context as Activity, listOf("email", "public_profile"))
        },
        modifier = Modifier
            .height(50.dp)
            .shadow(
                shape = MaterialTheme.shapes.small,
                ambientColor = Color.Gray,
                elevation = 8.dp
            ),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook),
            modifier = Modifier.size(24.dp),
            contentDescription = null
        )
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
                onLoginError(task.exception ?: Exception("Unknown error"))
            }
        }
}


@Preview(showSystemUi = true)
@Composable
fun LoginTemp() {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = balooFont,
                fontWeight = FontWeight.Bold
            )
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.cinema),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Weclcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                color = CustomBlue,
                fontFamily = balooFont,
                fontWeight = FontWeight.Bold
            )

        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Enter Your Details",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = balooFont,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            TextField(value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = CustomBlue,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTrailingIconColor = CustomBlue,
                    unfocusedTrailingIconColor = Color.Gray
                ),
                placeholder = {
                    Text(
                        text = "Email Address",
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.MailOutline, contentDescription = null
                    )
                })
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var passwordVisible by remember { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
            TextField(value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = CustomBlue,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTrailingIconColor = CustomBlue,
                    unfocusedTrailingIconColor = Color.Gray
                ),
                placeholder = {
                    Text(
                        text = "Password",
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff

                    // Localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    // Toggle button to hide or display password
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forget password?",
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = FontFamily.SansSerif,
                    color = CustomBlue,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    1.dp,
                    CustomBlue
                )
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = FontFamily.SansSerif,
                    color = CustomBlue,
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .background(Color.Black)
                    .height(1.dp)
            )
            Text(
                text = "Or Continue with",
                style = MaterialTheme.typography.titleSmall,
                fontFamily = FontFamily.SansSerif,
                color = CustomBlue,
            )
            Spacer(
                modifier = Modifier
                    .weight(0.5f)
                    .background(Color.Black)
                    .height(1.dp)
            )

        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            LoginSocialComp()
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            val annotatedText = buildAnnotatedString {
                append("Don't have an account? ")

                withStyle(style = SpanStyle(color = CustomBlue)) {
                    pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
                    append("Sign up")
                    pop()
                }
            }

            ClickableText(
                text = annotatedText,
                style = MaterialTheme.typography.bodyLarge,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                        .firstOrNull()?.let {

                        }
                }
            )
        }
    }
}

@Composable
fun LoginSocialComp() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .height(50.dp)
                .shadow(
                    shape = MaterialTheme.shapes.small,
                    ambientColor = Color.Gray,
                    elevation = 8.dp
                ), shape = MaterialTheme.shapes.small, colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )

        ) {
            Image(
                painter = painterResource(id = R.drawable.applelogo),
                modifier = Modifier.size(24.dp),
                contentDescription = null
            )
        }
        SignInGoogleScreen()
        SignInFacebookComp(onLoginSuccess = {
            Log.d("FacebookSignIn", "Firebase user email: ${it?.email}")
        }, onLoginError = {
            Log.e("FacebookSignIn", "Error: ${it.message}")
        })
    }
}


