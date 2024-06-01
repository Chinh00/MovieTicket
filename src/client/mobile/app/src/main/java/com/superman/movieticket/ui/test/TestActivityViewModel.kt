package com.superman.movieticket.ui.test

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.signalr.HubConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestActivityViewModel @Inject constructor(val hubConnection: HubConnection) : ViewModel() {

    val _signalrRes = MutableStateFlow("")
    val signalrRes = _signalrRes.asStateFlow()

    init {
    }
    fun SendMessage () {
        viewModelScope.launch {
            hubConnection.on("Receive", {s -> Log.d("Receive", s)}, String::class.java)
        }

        viewModelScope.launch {
            hubConnection.start().blockingAwait()
            hubConnection.send("SendMessage")
        }

    }
}