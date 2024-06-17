package com.superman.movieticket.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superman.movieticket.R
import com.superman.movieticket.ui.auth.control.ForgotPasswordViewModel
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.theme.CustomBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                ForgotPasswordComp()
            }, title = "Forgot Password", onNavigateUp = { finish() })

        }
    }

    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    @Composable
    @Preview(showSystemUi = true)
    private fun ForgotPasswordComp() {
        val viewModel: ForgotPasswordViewModel = viewModel()
        val context = LocalContext.current
        val email by viewModel.email.collectAsState()
        val scope = rememberCoroutineScope()
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .background(Color.White)
//                .paint(
//                    painterResource(id = R.drawable.mobilepasswordforgot),
//                    contentScale = ContentScale.FillWidth
//                )
            , contentAlignment = Alignment.TopStart
        ) {
            Column(modifier = Modifier.padding(top = 50.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.forgotpassword),
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "Forgot Password", modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.Black.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Default
                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Enter the email associated with your account",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "We'll send you the reset link",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Email",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextFieldWithError(
                        value = email.toString(),
                        onValueChange = {
                            viewModel.setEmail(it)
                        },
                        errorMessage = viewModel.errorE.collectAsState().value,
                        isError = viewModel.isFormValid.collectAsState().value==false,
                        placeholder = "Enter Email"
                    )
                    if(viewModel.isFormValid.collectAsState().value==true){
                        Dialog(
                            onDismissRequest = { false },
                            DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false
                            )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        scope.launch {

                            delay(2000)
                            val intent = Intent(
                                context.applicationContext,
                                SendEmailGetPasswordActivity::class.java
                            )
                            intent.putExtra("email", email)
                            context.startActivity(intent)
                        }
                    }
                    Button(
                        onClick = {
                            viewModel.validateForm()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CustomBlue),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Reset password")
                    }


                }
            }
        }

    }
}

@Composable
fun TextFieldWithError(
    value: String,
    onValueChange: (String) -> Unit, placeholder: String,
    isError: Boolean = false,
    errorMessage: String = "Error message",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = androidx.compose.ui.text.input.ImeAction.Send,
    onImeAction: () -> Unit = {}
) {
    var isErrorState by remember { mutableStateOf(isError) }
    val keyboardManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)

        },
        isError = isErrorState,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ), placeholder = { Text(placeholder) },
        keyboardActions = KeyboardActions(
                onSend = {keyboardManager.clearFocus()}
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
    )

    if (isErrorState) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}