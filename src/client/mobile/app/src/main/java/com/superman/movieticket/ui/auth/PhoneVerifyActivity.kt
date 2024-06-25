package com.superman.movieticket.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.superman.movieticket.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.theme.CustomBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneVerifyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {
            BaseScreen(content = {
                PhoneVerifyComp(onSendTo = { phone, code ->
                    val intent = Intent(this@PhoneVerifyActivity, PhoneOtpActivity::class.java)
                    intent.putExtra("phone", phone)
                    intent.putExtra("verifyId", code)
                    startActivity(intent)
                })
            }, title = "", onNavigateUp = { finish() })
        }
    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneVerifyComp(
    onSendTo: (String, String) -> Unit,
) {

    val loginActivityViewModel: LoginActivityViewModel = hiltViewModel()





    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var phone by remember { mutableStateOf("") }
    var selectedCode by remember { mutableStateOf(countryCodes[4].phone) }
    val verificationId by loginActivityViewModel.verificationId.collectAsState()

    var expanded by remember { mutableStateOf(false) }






    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mobilepasswordforgot),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            Text(
                text = "You'll receive a 4 digit code to verify next",
                style = MaterialTheme.typography.titleMedium.copy(Color.Gray),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    Text(
                        text = "Enter your mobile number",
                        style = MaterialTheme.typography.titleSmall.copy(Color.Gray),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Phone number input field
                    TextField(
                        value = phone,
                        onValueChange = { phone = it },
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = R.drawable.map),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = CustomBlue,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Country code dropdown
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        countryCodes.forEach { countryCode ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedCode = countryCode.phone
                                    expanded = false
                                }, text = {Text(text = countryCode.name)}
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Continue button
                Button(
                    onClick = {
                        isLoading = true
                        val fullPhoneNumber = selectedCode + phone
                        loginActivityViewModel.sendOtp(
                            fullPhoneNumber,
                            context as Activity
                        ) { success, verificationId ->
                            isLoading = false
                            if (success) {
                                onSendTo(fullPhoneNumber, verificationId)
                            } else {
                                // Handle verification failure
                                Log.e("PhoneVerifyComp", "Failed to send OTP")
                            }
                        }
                    },
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomBlue
                    ),
                    modifier = Modifier
                        .height(60.dp)
                        .padding(end = 2.dp)
                        .shadow(5.dp, shape = MaterialTheme.shapes.small)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(text = "Continue")
                    }
                }
            }
        }
    }
}

val countryCodes = listOf(
    Country("United States", "+1"),
    Country("United Kingdom", "+44"),
    Country("Australia", "+61"),
    Country("Germany", "+49"),
    Country("Vietnam", "+84")
    // Add more countries as needed
)

data class Country(
    val name: String,
    val phone: String
)
