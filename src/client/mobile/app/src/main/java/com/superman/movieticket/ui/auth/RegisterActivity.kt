package com.superman.movieticket.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.superman.movieticket.R

class RegisterActivity :ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            LoginScreen()
        }
    }


    @Composable
    @Preview(showBackground = true, showSystemUi = true)
    fun LoginScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.poster1),
                    contentScale = ContentScale.Crop, alignment = Alignment.TopCenter,
                    alpha = .9f
                )
                .background(color = Color(0x4D773131))
        ) {
            TextAuthContent("LOGIN", "")
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFieldCustom("Username", "Username", R.drawable.user)
                Spacer(modifier = Modifier.height(10.dp))
                TextFieldCustom("Password", "Password", R.drawable.padlock, R.drawable.show)
                Spacer(modifier = Modifier.height(5.dp))

                TextButton(onClick = {}){
                    Text(
                        text = "I've forgotten my password",
                        style = TextStyle(
                            color = Color.White,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }



                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDE7101)
                    )
                ) {
                    Text(text = "SIGN UP")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween, modifier =Modifier.fillMaxSize()
                ) {
                    Text(
                        text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(color=Color.White, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)){
                                                        append("Login ")
                                                    }
                            withStyle(style = SpanStyle(color=Color.White, fontWeight = FontWeight.Medium)){
                                append("with others")
                            }
                        },
                        style = TextStyle(
                            color = Color.LightGray,
                            fontSize = 19.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.wrapContentWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have account??",
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.wrapContentWidth()
                        )
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(
                                text = "Sign Up", color = Color(0xFFDE7101),
                                style = TextStyle(
                                    color = Color.LightGray,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center, fontWeight = FontWeight.ExtraBold
                                ),
                            )
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextFieldCustom(
        value: String,
        textHolder: String,
        @DrawableRes leadIcon: Int,
        @DrawableRes trailingIcon: Int? = 0
    ) {
        var textField by remember {
            mutableStateOf("")
        }
        TextField(
            value = "$value",
            label = { Text(text = "$textHolder") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadIcon),
                    contentDescription = "", Modifier.size(24.dp)
                )
            },
            trailingIcon = {
                if (trailingIcon == 0) {
                    null
                } else {

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = trailingIcon!!),
                            contentDescription = "",
                            Modifier.size(24.dp)
                        )
                    }

                }
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color(0x8FDFD0D0),
                unfocusedLeadingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White
            ),
            placeholder = { Text(text = "Enter Username", color = Color.LightGray) },
            onValueChange = { textField = it },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(
                        0x3C817676
                    )
                ),
        )

    }

    @Composable
    fun TextAuthContent(title: String, titleNote: String) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "MOVIES BOT", style = TextStyle(
                color = Color.Yellow, fontSize = 28.sp, textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.White, letterSpacing = 10.sp)
                ) {
                    append("WELCOME ")
                }

                withStyle(
                    style = SpanStyle(color = Color.White, letterSpacing = 10.sp)
                ) {
                    append("BACK!")
                }
            }, style = TextStyle(
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth()


        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title.uppercase(),
            style = TextStyle(
                letterSpacing = 4.sp,
                color = Color.White,
                fontSize = 23.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        if(titleNote.equals("")){
            null
        }else{
            Text(
                text = titleNote,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}