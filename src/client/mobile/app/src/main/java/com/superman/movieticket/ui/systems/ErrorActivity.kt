package com.superman.movieticket.ui.systems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ErrorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageError(message = intent.getStringExtra("error")!!)
        }
    }
}


@Composable
fun MessageError (message: String) {
    Scaffold {
        Surface (modifier = Modifier.apply {
            fillMaxSize().padding(it)
        }) {
            Column (modifier = Modifier.apply {
                fillMaxSize()
            }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = message)
            }
        }
    }
}