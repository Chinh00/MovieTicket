package com.superman.movieticket.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.superman.movieticket.infrastructure.utils.ApiState


@Composable
fun ScreenLoading (isLoading: State<ApiState>, content: @Composable () -> Unit) {
    if (isLoading.value == ApiState.LOADING) {
        Column (modifier = Modifier.apply {
            fillMaxSize()
        }, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(modifier = Modifier.apply {
                width(64.dp)
            }, color = MaterialTheme.colorScheme.secondary, trackColor = MaterialTheme.colorScheme.surfaceVariant)
        }
    } else {
        content()
    }

}
