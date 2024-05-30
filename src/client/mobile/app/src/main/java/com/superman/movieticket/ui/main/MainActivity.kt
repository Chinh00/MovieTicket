package com.superman.movieticket.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.detail.view.DetailScreen
import com.superman.movieticket.ui.detail.view.ListScreen
import com.superman.movieticket.ui.home.HomeScreen
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
        title = "Trang chủ",
        path = "home"
    ),
    NavigationBarItemConfig(
        Icons.Filled.Search,
        onClick = {},
        title = " Tìm kiếm",
        path = "search"
    ),
    NavigationBarItemConfig(
        Icons.Filled.Settings,
        onClick = {},
        title = "Tài khoản",
        path = "profile"
    ),

)

@Composable
@Preview
fun MainScreen () {
    val context = LocalContext.current
    val navController = rememberNavController()
    Scaffold (
        topBar = {

        },
        bottomBar = {
            NavigationBar {

                navigationBarItems.forEach {
                    item ->
                    NavigationBarItem(selected = 1 == 1, onClick = { navController.navigate(item.path) }, icon = { Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    ) })
                }
            }

        }
    ) {
        Surface (modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            NavHost(navController = navController, startDestination = "list" ) {
                composable("home") { HomeScreen()}
                composable("list") { ListScreen (context) }
//                composable("detail/{id}/{s}", arguments = listOf(
//                    navArgument("id"){
//                        type = NavType.IntType
//                    },
//                    navArgument("s"){
//                        type = NavType.StringType
//                    }
//                )
//                ) {
//                    val id=it.arguments?.getInt("id")
//                    val s=it.arguments?.getString("s")
//                    DetailScreen(id!!.toInt(),s.toString())
//                }s
            }
        }
    }
}

