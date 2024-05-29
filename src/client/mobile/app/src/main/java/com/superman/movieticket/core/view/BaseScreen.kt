package com.superman.movieticket.core.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Navigation example")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }
    ){
        Column (modifier = Modifier.apply {
            padding(it).fillMaxWidth()
        }) {
            content()
        }
    }
}
