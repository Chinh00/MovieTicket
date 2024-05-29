package com.superman.movieticket.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.superman.movieticket.ui.main.model.NavigationBarItemConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }

}

val navigationBarItems = listOf<NavigationBarItemConfig>(
    NavigationBarItemConfig(
        Icons.Filled.Home,
        onClick = {},
        title = "Trang chủ"
    ),
    NavigationBarItemConfig(
        Icons.Filled.Search,
        onClick = {},
        title = " Tìm kiếm"
    ),
    NavigationBarItemConfig(
        Icons.Filled.Settings,
        onClick = {},
        title = "Tài khoản"
    ),

)

@Composable
@Preview
fun MainScreen () {

    Scaffold (
        bottomBar = {
            NavigationBar {

                navigationBarItems.forEach {
                    item ->
                    NavigationBarItem(selected = 1 == 1, onClick = { /*TODO*/ }, icon = { Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    ) })
                }
            }

        }
    ) {
        Text(text = "", Modifier.padding(it))
    }
}

