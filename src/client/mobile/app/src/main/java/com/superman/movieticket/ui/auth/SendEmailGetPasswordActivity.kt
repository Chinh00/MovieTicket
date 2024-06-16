package com.superman.movieticket.ui.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.lights.Light
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superman.movieticket.R
import com.superman.movieticket.ui.auth.control.ForgotPasswordState
import com.superman.movieticket.ui.auth.control.OtpViewModel
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.theme.CustomBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SendEmailGetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                SendEmailGetPasswordComp(OtpViewModel(), intent.getStringExtra("email").toString())
            }, title = "Forgot Password", onNavigateUp = { finish() })

        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun SendEmailGetPasswordComp(viewModel: OtpViewModel, email: String) {
        val scope = rememberCoroutineScope()

        val context = LocalContext.current
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
                        painter = painterResource(id = R.drawable.email),
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "Check Your Email", modifier = Modifier.padding(vertical = 10.dp),
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
                        text = "We've sent password reset instructions to",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = email,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    var ValidCode by remember { mutableStateOf(true) }
                    OtpTextFieldCutom(otpLength = 6, ValidCode = ValidCode, viewModel) { otp ->
                        if (otp != "123456") ValidCode = false
                        else {
                            ValidCode = true
                            viewModel.isCheckCode()
                        }

                    }
                    AnimatedVisibility(visible = viewModel.state.collectAsState().value == ForgotPasswordState.Loading) {
                        Text(
                            text = "Not valid Code verified value",
                            color = Color.Red.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                        )
                    }
                    var showDialog by remember { mutableStateOf(false) }
                    if (viewModel.state.collectAsState().value == ForgotPasswordState.Success) {
                        Dialog(
                            onDismissRequest = { showDialog = false },
                            DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false
                            )
                        ) {
                            Box(
                                contentAlignment = Center,
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(White, shape = RoundedCornerShape(8.dp))
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        scope.launch {
                            delay(2000)
                            val intent = Intent(
                                context.applicationContext,
                                ChangePasswordActivity::class.java
                            )
                            context.startActivity(intent)

                        }
                    }
                    Button(
                        onClick = {
                            val intent = Intent(
                                context.applicationContext,
                                ChangePasswordActivity::class.java
                            )
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CustomBlue),
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Verify Code and is Human")
                    }


                }
            }
        }
    }


    @Composable
    fun OtpTextFieldCutom(
        otpLength: Int,
        ValidCode: Boolean? = null, viewModel: OtpViewModel,
        onOtpComplete: (String) -> Unit
    ) {
        var otpValue by remember { mutableStateOf("") }

        val focusRequesters = remember { List(otpLength) { FocusRequester() } }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        BasicTextField(
            value = otpValue,
            onValueChange = {
                if (it.length <= 6) {
                    otpValue = it

                }
                if (otpValue.length == otpLength) {
                    viewModel.setOtpValue(otpValue)
                    onOtpComplete(otpValue)
                    keyboardController?.hide()
                    viewModel.isCheckCode()
                }


            }, cursorBrush = SolidColor(CustomBlue),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,

                ), visualTransformation = PasswordVisualTransformation(),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpLength) { index ->
                        val char = when {
                            index >= otpValue.length -> ""
                            else -> otpValue[index].toString()
                        }
                        val state by viewModel.state.collectAsState()
                        val isOk = when {
                            char.isNotEmpty() && ValidCode == true || state == ForgotPasswordState.Success -> Color.Green
                            else -> MaterialTheme.colorScheme.error
                        }
                        Log.d("index", index.toString())
                        Text(
                            text = char, lineHeight = 50.sp,
                            modifier = Modifier
                                .size(50.dp)
                                .border(2.dp, isOk, MaterialTheme.shapes.medium)
                                .padding(2.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center
                            ),
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

            }
        )
    }

//    @Composable
//    @Preview(showBackground = true)
//    fun OtpPre() {
//        MaterialTheme {
//           SendEmailGetPasswordComp(OtpViewModel())
//        }
//    }


}