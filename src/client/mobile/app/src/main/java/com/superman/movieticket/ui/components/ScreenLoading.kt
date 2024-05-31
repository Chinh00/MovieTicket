package com.superman.movieticket.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ScreenLoading (isLoading: State<Boolean>, content: @Composable () -> Unit) {
    if (isLoading.value) {
        Column (modifier = Modifier.apply {
            fillMaxSize()
        }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        }
    } else {
        content()
    }

}
