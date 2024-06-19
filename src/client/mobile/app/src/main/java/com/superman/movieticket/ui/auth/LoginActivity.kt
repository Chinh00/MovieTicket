package com.superman.movieticket.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.control.LoginActivityModelImpl
import dagger.hilt.android.AndroidEntryPoint
import com.superman.movieticket.R
import com.superman.movieticket.ui.auth.control.LoginGoogleViewModel
import com.superman.movieticket.ui.auth.control.LoginFaceBookViewModel
import com.superman.movieticket.ui.auth.control.LoginSocialViewModel
import com.superman.movieticket.ui.theme.CustomBlue
import com.superman.movieticket.ui.theme.balooFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
public class LoginActivity : ComponentActivity() {
    private val loginFacebookViewModel: LoginFaceBookViewModel by viewModels()
    private val loginGoogleViewModel: LoginGoogleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            com.superman.movieticket.ui.components.BaseScreen(content = {
                LoginScreen()
            }, title = "")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            loginGoogleViewModel.signInWithGoogle(task) {
                if (it) {
                    finish()
                } else {
                    Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                }

            }
        }else{
            Log.d("LoginActivity: ", requestCode.toString() + " réult code " + resultCode)
            loginFacebookViewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }



    companion object {
         const val RC_SIGN_IN = 9001
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(

) {
    val loginGoogleViewModel: LoginGoogleViewModel = hiltViewModel()

    val context = LocalContext.current


    Box (modifier = Modifier.apply {
        fillMaxSize().background(Color.Gray)
    }, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
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
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = {
                    val intent = Intent(context.applicationContext,ForgotPasswordActivity::class.java)

                    context.startActivity(intent) }) {
                    Text(
                        text = "Forget password?",
                        style = MaterialTheme.typography.labelLarge,
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
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
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

                ClickableText(text = annotatedText,
                    style = MaterialTheme.typography.bodyLarge,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                            .firstOrNull()?.let {
                                Log.d("weew", "asdasdasdasdasdasd")
                            }
                    })
            }
        }

        if (loginGoogleViewModel.apiLoading.collectAsState().value == ApiState.LOADING) {
            CircularProgressIndicator(
                modifier = Modifier.width(70.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }

}



