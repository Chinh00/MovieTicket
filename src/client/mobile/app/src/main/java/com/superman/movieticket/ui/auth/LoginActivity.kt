package com.superman.movieticket.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.superman.movieticket.core.view.BaseActivity
import com.superman.movieticket.core.view.BaseScreen
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.control.LoginActivityModel
import com.superman.movieticket.ui.auth.control.LoginActivityModelImpl
import com.superman.movieticket.ui.auth.model.UserLoginModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.superman.movieticket.R
import com.superman.movieticket.ui.auth.control.AuthViewModel
import com.superman.movieticket.ui.auth.control.LoginFaceBookViewModel
import com.superman.movieticket.ui.auth.control.LoginSocialViewModel
import com.superman.movieticket.ui.components.ButtonLoading
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.theme.CustomBlue
import com.superman.movieticket.ui.theme.balooFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
public class LoginActivity : ComponentActivity() {
    private val viewModel: LoginFaceBookViewModel by viewModels()
    private val ggviewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            com.superman.movieticket.ui.components.BaseScreen(content = {
                LoginScreen(ggviewModel)
            }, title = "")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



        if(requestCode ==RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d("LoginActivity: ", requestCode.toString() + " réult code " + resultCode)

            ggviewModel.signInWithGoogle(task) { success ->
                if (success) {
                    // Handle successful sign-in

                } else {
                    // Handle sign-in failure

                }
            }
        }else{
            Log.d("LoginActivity: ", requestCode.toString() + " réult code " + resultCode)
            viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
    companion object {
         const val RC_SIGN_IN = 9001
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    ggViewModel:AuthViewModel
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val loginActivityModelImpl: LoginActivityModelImpl = hiltViewModel()
    val loginSocialModelImpl: LoginSocialViewModel = hiltViewModel()
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    val context = LocalContext.current
    var username by remember {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    fun HandleLogin() {
        val userLoginModel = UserLoginModel()
        userLoginModel.username = username
        userLoginModel.password = password
        coroutineScope.launch {
            loginActivityModelImpl.HandleLoginAction(userLoginModel)
            loginActivityModelImpl._apiState.collect {
                when (it) {
                    ApiState.SUCCESS -> {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }

                    ApiState.FAIL -> {
                        Toast.makeText(
                            context,
                            "Tài khoản mật khẩu không chính xác",
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                    ApiState.LOADING -> {

                    }

                    ApiState.NONE -> {

                    }
                }
            }
        }

    }



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
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                TextField(value = username,
                    onValueChange = { username = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = CustomBlue,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTrailingIconColor = CustomBlue,
                        unfocusedTrailingIconColor = Color.Gray,
                        focusedContainerColor = Color.Transparent

                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
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

            var passwordVisible by remember { mutableStateOf(false) }
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                TextField(value = password,
                    onValueChange = { password = it },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.moveFocus(FocusDirection.Down) }

                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = CustomBlue,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTrailingIconColor = CustomBlue,
                        unfocusedTrailingIconColor = Color.Gray,
                        focusedContainerColor = Color.Transparent
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
                        val image = if (passwordVisible) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff

                        // Localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        // Toggle button to hide or display password
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    })
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

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                ButtonLoading(content = {
                    Text(
                        text = "Đăng nhập ",
                        modifier = Modifier.apply { fillMaxSize() })
                }, isLoading = 1 != 1, modifier = Modifier.apply {
                    padding(5.dp).fillMaxWidth()
                }, colors = ButtonDefaults.buttonColors(CustomBlue), onClick = {
                    HandleLogin()
                })

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

        if (loginSocialModelImpl.apiLoading.collectAsState().value == ApiState.LOADING) {
            CircularProgressIndicator(
                modifier = Modifier.width(70.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }

}



