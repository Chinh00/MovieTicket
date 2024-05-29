package com.superman.movieticket.ui.auth

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.superman.movieticket.core.view.BaseActivity
import com.superman.movieticket.core.view.BaseScreen
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.control.LoginActivityModel
import com.superman.movieticket.ui.auth.control.LoginActivityModelImpl
import com.superman.movieticket.ui.auth.model.UserLoginModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
public class LoginActivity : BaseActivity<LoginActivityModelImpl>() {
    override fun getViewModel(): Class<LoginActivityModelImpl> = LoginActivityModelImpl::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(lifecycleScope = lifecycleScope, model = _model)
        }
    }


}


@Composable
fun HomeScreen(
    lifecycleScope: LifecycleCoroutineScope,
    model: LoginActivityModel
) {
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    fun HandleLogin () = {

    }
    BaseScreen {
        Column (modifier = Modifier.apply {
            fillMaxSize().padding(10.dp)
        }){
            TextField(value = username.value, onValueChange = {username.value = it}, label = { Text(
                text = "Username"
            )})
            TextField(value = password.value, onValueChange = {password.value = it}, label = { Text(
                text = "Password"
            )})

        }
    }
}


