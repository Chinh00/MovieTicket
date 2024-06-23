package com.superman.movieticket.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.auth.hooks.NavigateLogin
import com.superman.movieticket.ui.film.FilmScreen
import com.superman.movieticket.ui.home.HomeScreen
import com.superman.movieticket.ui.main.model.NavigationBarItemConfig
import com.superman.movieticket.ui.profile.ProfileScreen
import com.superman.movieticket.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                false
            }
        }
        // Chỉ gọi setContent một lần
        if (savedInstanceState == null) {
            setContent {
                requestNotificationPermission()
                MyAppTheme {
                    MainScreen()
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        // Kiểm tra quyền POST_NOTIFICATIONS nếu là Android 13 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFICATION_PERMISSION_CODE
                )
            } else {
                // Quyền đã được cấp
//                Toast.makeText(this, "Quyền hiển thị thông báo đã được cấp.", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Không cần kiểm tra quyền với các phiên bản Android cũ hơn
//            Toast.makeText(this, "Không cần yêu cầu quyền trên Android dưới 13.", Toast.LENGTH_SHORT).show()
        }
    }

    // Xử lý kết quả yêu cầu quyền
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Quyền đã được cấp
                Toast.makeText(this, "Quyền hiển thị thông báo đã được cấp.", Toast.LENGTH_SHORT).show()
            } else {
                // Quyền bị từ chối
                Toast.makeText(this, "Quyền hiển thị thông báo bị từ chối.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        const val REQUEST_NOTIFICATION_PERMISSION_CODE = 1001
    }
}


@SuppressLint("AutoboxingStateCreation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen () {
    val navController = rememberNavController()
    val context = LocalContext.current
    val loginSocialViewModel: LoginActivityViewModel = hiltViewModel()
    val login by loginSocialViewModel.isLogin.collectAsState(initial = null)
    val navigationBarItems = listOf<NavigationBarItemConfig>(
        NavigationBarItemConfig(
            Icons.Filled.Home,
            onClick = {
                navController.navigate("home")
            },
            title = context.getString(com.superman.movieticket.R.string.home),
            path = "home"
        ),
        NavigationBarItemConfig(
            Icons.Filled.EmergencyRecording,
            onClick = {
                navController.navigate("film")
            },
            title = context.getString(com.superman.movieticket.R.string.film),
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
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.onBackground
            ),title = { Text(text = "Movie Ticket") })
        },
        bottomBar = {
            var selectedItem  = remember {
                mutableStateOf(0)
            }
            NavigationBar(containerColor = MaterialTheme.colorScheme.background, modifier = Modifier.shadow(elevation = 8.dp,shape= RectangleShape, ambientColor = Color.Gray, spotColor = Color.Gray)) {
                navigationBarItems.forEachIndexed {
                   index,item ->
                    NavigationBarItem(selected = selectedItem.value == index, label = { Text(text = item.title)}, onClick = {
                        item.onClick()
                        selectedItem.value = index
                    }, icon = { Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    ) }, colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,

                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,

                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ))
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

