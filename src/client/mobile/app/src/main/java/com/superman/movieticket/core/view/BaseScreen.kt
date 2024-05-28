package com.superman.movieticket.core.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun BaseScreen(content: @Composable () -> Unit) {
    Scaffold{
        Column (modifier = Modifier.apply {
            padding(it).fillMaxWidth()
        }) {
            content()
        }
    }
}
