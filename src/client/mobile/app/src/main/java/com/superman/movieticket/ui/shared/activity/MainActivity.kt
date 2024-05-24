package com.superman.movieticket.ui.shared.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superman.movieticket.MovieTicketApplication
import com.superman.movieticket.R
import com.superman.movieticket.core.view.ActivityBase
import com.superman.movieticket.databinding.ActivityMainBinding
import com.superman.movieticket.ui.home.HomeFragment
import com.superman.movieticket.ui.navigation.MainScreen
import com.superman.movieticket.ui.news.NewsFragment
import com.superman.movieticket.ui.shared.activity.control.MainActivityModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ActivityBase<ActivityMainBinding, MainActivityModel>() {
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getViewModel(): Class<MainActivityModel> = MainActivityModel::class.java

    override fun setupViews() {
        switchFragment(HomeFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            MyApp{
                MainScreen()
            }
        }
    }



    override fun setupActions() {
        _binding.bottomBar.apply {
            setOnItemSelectedListener { t -> when(t) {
                0 -> {
                    switchFragment(HomeFragment())
                }
                1 -> {
                    switchFragment(NewsFragment())
                }
            } }
        }


    }
    @SuppressLint("CommitTransaction")
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations( com.google.android.material.R.anim.abc_slide_in_bottom,
            com.google.android.material.R.anim.abc_fade_out, // exit
            com.google.android.material.R.anim.abc_fade_in, // popEnter
            com.google.android.material.R.anim.abc_slide_out_top )
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }



}

@Composable
fun MyApp(content : @Composable () -> Unit){
    MaterialTheme {
        Surface (modifier = Modifier.fillMaxSize()){
            content()
        }
    }
}
@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile Screen", fontSize = 24.sp)
    }
}

@Composable
fun SearchScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Search Screen", fontSize = 24.sp)
    }
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Search", Icons.Default.Search, "search"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    NavigationBar(

        contentColor = Color.Black, containerColor = Color(0xD0363636)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
