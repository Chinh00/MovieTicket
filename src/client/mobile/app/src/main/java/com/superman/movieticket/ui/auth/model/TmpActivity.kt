package com.superman.movieticket.ui.auth.model

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.auth.ChangePasswordActivity
import com.superman.movieticket.ui.auth.control.PhoneOtpActivityViewModel
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.profile.control.UpdateActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class TmpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val updateActivityViewModel: UpdateActivityViewModel = hiltViewModel()





            val context = LocalContext.current

            val user = updateActivityViewModel.userinfo.collectAsState()

            val isLoading = updateActivityViewModel.apiLoading.collectAsState()

            if (isLoading.value == ApiState.SUCCESS ) {
                when (user.value?.passwordHash) {
                    null -> {
                        runOnUiThread {
                            val intent = Intent(context, ChangePasswordActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP )
                            context.startActivity(intent)
                        }
                    }
                    else -> {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP )
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}