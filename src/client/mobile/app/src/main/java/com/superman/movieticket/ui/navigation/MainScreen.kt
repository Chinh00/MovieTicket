package com.superman.movieticket.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.superman.movieticket.ui.favourite.FavouriteScreen
import com.superman.movieticket.ui.home.HomeScreen
import com.superman.movieticket.ui.shared.activity.BottomNavigationBar
import com.superman.movieticket.ui.shared.activity.ProfileScreen
import com.superman.movieticket.ui.shared.activity.TicketScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController)
    }
}
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen() }
        composable("favourite") { FavouriteScreen() }
        composable("ticket") { TicketScreen() }
        composable("profile") { ProfileScreen() }
    }
}