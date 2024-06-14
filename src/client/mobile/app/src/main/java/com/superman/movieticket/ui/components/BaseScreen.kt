package com.superman.movieticket.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen (
    content: @Composable () -> Unit,
    title: String?,onNavigateUp:(()->Unit)?=null

){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors= TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent, titleContentColor = MaterialTheme.colorScheme.background
            ),
                title = {
                    title?.let { Text(text = it, textAlign = TextAlign.Center) }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp?.invoke()
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },

    ){
        Surface(modifier = Modifier.padding(it)) {
            content()
        }
    }
}