package com.superman.movieticket.ui.auth


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superman.movieticket.R
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.theme.CustomBlue

class ChangePasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                ChangePasswordComp()
            }, title = "Forgot Password")

        }
    }

    @Composable
    @Preview(showSystemUi = true)
    private fun ChangePasswordComp() {
        val context = LocalContext.current
        var mk by remember {
            mutableStateOf("")
        }
        var rmk by remember {
            mutableStateOf("")
        }
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
                        painter = painterResource(id = R.drawable.synchronize),
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "Set New Password", modifier = Modifier.padding(vertical = 10.dp),
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
                        text = "Enter a new password your account",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )


                }
                Spacer(modifier = Modifier.height(20.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Password",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    OutlinedTextField(
                        value = mk,
                        onValueChange = { mk = it },
                        placeholder = { Text("Create a password", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.Gray, MaterialTheme.shapes.large)
                            .clip(MaterialTheme.shapes.large)


                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Confirm password",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    OutlinedTextField(
                        value = rmk,
                        onValueChange = { rmk = it },
                        placeholder = { Text("Confirm password", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, Color.Gray, MaterialTheme.shapes.large)
                            .clip(MaterialTheme.shapes.large)


                    )
                    Button(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    context.applicationContext,
                                    DoneChangePasswordActivity::class.java
                                )
                            )
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