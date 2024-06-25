package com.superman.movieticket.ui.auth



import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superman.movieticket.R
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.control.ForgotPasswordState
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.auth.control.OtpViewModel
import com.superman.movieticket.ui.auth.control.PhoneOtpActivityViewModel
import com.superman.movieticket.ui.auth.model.TmpActivity
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.theme.CustomBlue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhoneOtpActivity : ComponentActivity() {
    private val loginActivityViewModel: LoginActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {

                loginActivityViewModel.setVerifyCode(
                    intent.getStringExtra("verifyId").toString()
                )

                Log.d("code1", intent.getStringExtra("verifyId").toString())
                Log.d("phone", intent.getStringExtra("phone").toString())
                SendPhoneGetOtpComp(
                    phone = intent.getStringExtra("phone").toString(),
                    onTimeout = {

                        finish() })
            }, title = "", onNavigateUp = { finish() })
        }
    }

    @Composable
    fun OtpTextFieldCutom(
        otpLength: Int, onVerified: (Boolean) -> Unit,
        finish: () -> Unit
    ) {
        val loginActivityViewModel: LoginActivityViewModel = hiltViewModel()


        var otpValue by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        val context = LocalContext.current



        val isVerify = remember {
            mutableStateOf(false)
        }



        BasicTextField(
            value = otpValue,
            onValueChange = {
                if (it.length <= 6) {
                    otpValue = it

                }

                if (otpValue.length == otpLength) {
                    loginActivityViewModel.setOtpValue(otpValue)
                    keyboardController?.hide()
                    loginActivityViewModel.verifyOtp(otpValue) { verified ->
                        val intent = Intent(context, TmpActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP )
                        context.startActivity(intent)


                        onVerified(verified)
                        isVerify.value = verified
                        if (verified ) {
                            runOnUiThread {

                            }
                        }

                    }
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
                        val isOk = when {
                            char.isNotEmpty() || isVerify.value -> Color.Green
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

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun SendPhoneGetOtpComp(
        phone: String,
        onTimeout: () -> Unit,
    ) {
        val loginActivityViewModel: LoginActivityViewModel = hiltViewModel()

        val scope = rememberCoroutineScope()
        val verifyCode by loginActivityViewModel.verificationId.collectAsState()
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) }
        val timeLeft by loginActivityViewModel.timeLeft.collectAsState()
        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .background(Color.White)
            , contentAlignment = Alignment.TopStart
        ) {
            Column(modifier = Modifier.padding(top = 50.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sms),
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "Check Your SMS", modifier = Modifier.padding(vertical = 10.dp),
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
                        text = "Enter the OTP sent to",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = phone,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
                Column(modifier = Modifier.fillMaxWidth()) {

                    OtpTextFieldCutom(otpLength = 6, onVerified = {showDialog = it}) {
                        onTimeout()
                    }
                    if (showDialog) {
                        Dialog(
                            onDismissRequest = { showDialog = false },
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
                    }


                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Time left: $timeLeft seconds",
                        style = MaterialTheme.typography.titleSmall.copy(Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Didn't receive the OTP? ")
                    TextButton(
                        onClick = {
                            loginActivityViewModel.sendOtp(
                                phone,
                                context as ComponentActivity,
                                onOtpSent = { success, s ->
                                })
                        }
                    ) {
                        Text(
                            text = "RESEND OTP",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = CustomBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}