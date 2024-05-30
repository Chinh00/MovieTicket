package com.superman.movieticket.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.superman.movieticket.core.view.BaseActivity
import com.superman.movieticket.ui.auth.control.LoginActivityModel
import com.superman.movieticket.ui.auth.control.LoginActivityModelImpl
import com.superman.movieticket.ui.auth.model.UserLoginModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.superman.movieticket.R
import com.superman.movieticket.ui.components.ButtonLoading
import com.superman.movieticket.ui.main.MainActivity

@AndroidEntryPoint
public class LoginActivity : BaseActivity<LoginActivityModelImpl>() {
    override fun getViewModel(): Class<LoginActivityModelImpl> = LoginActivityModelImpl::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(_model, lifecycleScope, this)
        }
    }


}


@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun LoginScreen(
    model: LoginActivityModel,
    lifecycleScope: LifecycleCoroutineScope,
    context: Context
) {
    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    fun HandleLogin () {
        lifecycleScope.launch {
            val userLoginModel = UserLoginModel()
            userLoginModel.username = username.value
            userLoginModel.password = password.value
            model.HandleLoginAction(userLoginModel)
        }
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)

    }



    Scaffold (modifier = Modifier.apply {
        fillMaxSize()
    }) {


        Box (modifier = Modifier.apply {
            fillMaxSize().padding(it)
        }, contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.conan),
                contentDescription = "Sample JPG Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            FlowRow (modifier = Modifier.apply {
                padding(10.dp)
            }, horizontalArrangement = Arrangement.Center, verticalArrangement = Arrangement.Top) {
                Column (modifier = Modifier.apply { fillMaxSize() }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(value = username.value, onValueChange = {username.value = it}, label = { Text(
                        text = "Tên đăng nhập"
                    )} )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(value = password.value, onValueChange = {password.value = it}, label = { Text(
                        text = "Mật khẩu"
                    )} , modifier = Modifier.apply {

                    })
                    Spacer(modifier = Modifier.height(10.dp))

                    ButtonLoading(content = {Text(text = "Đăng nhập ", modifier = Modifier.apply { fillMaxSize() })}, isLoading = 1 != 1, modifier = Modifier.apply {
                        padding(5.dp)
                    }, colors = ButtonDefaults.buttonColors(Color.Red), onClick = {
                        HandleLogin()
                    })

                }
            }

        }
    }
}


