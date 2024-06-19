package com.superman.movieticket.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmergencyRecording
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.auth.control.LoginSocialViewModel
import com.superman.movieticket.ui.auth.hooks.NavigateLogin
import com.superman.movieticket.ui.detail.view.DetailScreen
import com.superman.movieticket.ui.film.FilmScreen
import com.superman.movieticket.ui.home.HomeScreen
import com.superman.movieticket.ui.main.model.NavigationBarItemConfig
import com.superman.movieticket.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen () {
    val navController = rememberNavController()
    val context = LocalContext.current
    val loginSocialViewModel: LoginSocialViewModel = hiltViewModel()
    val login by loginSocialViewModel.isLogin.collectAsState(initial = null)
    val navigationBarItems = listOf<NavigationBarItemConfig>(
        NavigationBarItemConfig(
            Icons.Filled.Home,
            onClick = {
                navController.navigate("home")
            },
            title = "Trang chủ",
            path = "home"
        ),
        NavigationBarItemConfig(
            Icons.Filled.EmergencyRecording,
            onClick = {
                navController.navigate("film")
            },
            title = "Phim",
            path = "film"
        ),
        NavigationBarItemConfig(
            Icons.Filled.Settings,
            onClick = {
                if (login != "true") {
                    NavigateLogin(context)
                } else {
                    navController.navigate("profile")
                }
            },
            title = "Tài khoản",
            path = "profile"
        ),

        )


    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Movie Ticket") })
        },
        bottomBar = {
            NavigationBar {
                navigationBarItems.forEach {
                    item ->
                    NavigationBarItem(selected = 1 == 1, label = { Text(text = item.title)}, onClick = {
                        item.onClick()

                    }, icon = { Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    ) })
                }
            }

        }
    ) {
        Surface (modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            NavHost(navController = navController, startDestination = "home" ) {
                composable("home") { HomeScreen()}
                composable("film") { FilmScreen() }
                composable("profile") { ProfileScreen() }
            }
        }
    }
}

