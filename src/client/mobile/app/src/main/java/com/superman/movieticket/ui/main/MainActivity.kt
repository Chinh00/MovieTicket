package com.superman.movieticket.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {


}

@Composable
@Preview
fun HomeScreen () {

    Scaffold {
        Text(text = "", Modifier.padding(it))
    }
}

