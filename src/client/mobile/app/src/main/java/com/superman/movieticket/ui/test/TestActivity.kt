package com.superman.movieticket.ui.test

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.signalr.HubConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TestActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestScreen()
        }
    }
}

@Composable
fun TestScreen () {
    val testActivityViewModel: TestActivityViewModel = hiltViewModel()
    /*LaunchedEffect(key1 = Unit) {
        testActivityViewModel.hubConnection.on("Receive", {data -> Log.d("Chinh", data)}, String::class.java)
    }*/
        LaunchedEffect(key1 = Unit) {

        }

    Button(onClick = {
        testActivityViewModel.SendMessage()
    }) {
        Text(text = "Conenct")
    }

}

