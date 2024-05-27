package com.superman.movieticket.ui.shared.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
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
//        switchFragment(HomeFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContent {
            MyApp {
                MainScreen()
            }
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun MyAppPre() {
        MyApp {
            MainScreen()
        }
    }

    override fun setupActions() {
//        _binding.bottomBar.apply {
//            setOnItemSelectedListener { t -> when(t) {
//                0 -> {
//                    switchFragment(HomeFragment())
//                }
//                1 -> {
//                    switchFragment(NewsFragment())
//                }
//            } }
//        }


    }

    @SuppressLint("CommitTransaction")
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction().setCustomAnimations(
            com.google.android.material.R.anim.abc_slide_in_bottom,
            com.google.android.material.R.anim.abc_fade_out, // exit
            com.google.android.material.R.anim.abc_fade_in, // popEnter
            com.google.android.material.R.anim.abc_slide_out_top
        )
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }


}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {

            Column {
                HeaderContent(name = "DOdong")
                content()
            }
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
fun TicketScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Ticket Screen", fontSize = 24.sp)
    }
}
@Composable
fun HeaderContent(name: String) {

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().background(Color.Black).padding(horizontal = 15.dp)) {
        Column {
            androidx.compose.material3.Text(
                text = "Hello, $name",
                color = Color.White,
                fontSize = 18.sp
            )
            androidx.compose.material3.Text(
                text = "Let's book your favourite film",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .shadow(elevation = 5.dp)
                .clip(
                    CircleShape
                )
                .size(50.dp)
        )
    }


}
@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.home_outline, R.drawable.home_fill, "home"),
        BottomNavItem("Ticket", R.drawable.ticket_outline, R.drawable.ticket_fill, "ticket"),
        BottomNavItem("Favourite", R.drawable.heart_outline, R.drawable.heart_fill, "favourite"),
        BottomNavItem("Profile", R.drawable.user_icon_oulined, R.drawable.user_fill, "profile")
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Row(
        modifier = Modifier.padding(horizontal = 10.dp)
            .fillMaxWidth().height(60.dp)
            .clip(RoundedCornerShape(10.dp))

            .background(Color(0xFFFFEB3B)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        items.forEach { item ->
            val selected = currentRoute == item.route
            val contentColor = if (selected) Color.White else Color.White
            val bg = if (selected) Color(0xFF3F51B5) else Color(0xFFABB5F1)
            val paint =
                if (selected) painterResource(id = item.iconFill) else painterResource(id = item.iconOutline)
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable {
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
                    .background(bg).clip(CircleShape)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Icon(
                        painter = paint,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = contentColor
                    )
                    AnimatedVisibility(visible = selected) {
                        Text(text = item.label, color = Color.White)
                    }
                }
            }
        }
    }
}


data class BottomNavItem(
    val label: String,
    @DrawableRes val iconOutline: Int,
    @DrawableRes val iconFill: Int,
    val route: String
)
