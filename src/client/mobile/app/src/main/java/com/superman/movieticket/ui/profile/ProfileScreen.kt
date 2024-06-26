package com.superman.movieticket.ui.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.ui.history.MyOrderActivity
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.profile.control.ProfileScreenViewModel
import com.superman.movieticket.ui.profile.control.UpdateActivityViewModel

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()
    val updateActivityViewModel: UpdateActivityViewModel = hiltViewModel()
    val user = updateActivityViewModel.userinfo.collectAsState().value?:null
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

            }
            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = user?.avatar?:"",
                        error = painterResource(id = R.drawable.avatar)
                    ), // Thay đổi ID ảnh tương ứng
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable {
                            context.startActivity(Intent(context, UpdateActivity::class.java))
                        },
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = user?.fullName ?: "Hãy cập nhật",
                color = Color.Black,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.Center)
                    .padding(top = 20.dp)
            )
            Text(
                text = "@${user?.email ?: "Hãy cập nhật"}",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.Center)
            )

            Spacer(modifier = Modifier.height(40.dp))
            // Thêm phần thanh ngang
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val intent = Intent(context, UpdateActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_settings_24),
                    contentDescription = "Update Account",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(top = 5.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Update Account",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold // Nếu muốn làm đậm chữ
                )
                Spacer(modifier = Modifier.width(88.dp)) // Khoảng cách giữa Text và Icon thứ hai
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "Update Account",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.padlock),
                    contentDescription = "Change Password",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(top = 5.dp),
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Icon và Text
                Text(
                    text = "Change Password",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold // Nếu muốn làm đậm chữ
                )
                Spacer(modifier = Modifier.width(65.dp)) // Khoảng cách giữa Text và Icon thứ hai
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "Change Password",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                )

            }

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    val intent = Intent(context, MyOrderActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_list_alt_24),
                    contentDescription = "My Order",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(top = 5.dp),
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Icon và Text
                Text(
                    text = "My Order",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold // Nếu muốn làm đậm chữ
                )
                Spacer(modifier = Modifier.width(165.dp)) // Khoảng cách giữa Text và Icon thứ hai
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "My Order",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                )

            }
            Spacer(modifier = Modifier.height(70.dp))
            // Thêm phần thanh ngang
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                profileScreenViewModel.HandleLogout()
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }, modifier = Modifier.apply {
                background(Color.Red)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                    contentDescription = "Logout",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .size(55.dp)
                        .padding(top = 5.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Đăng xuất",
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun ProfileActivity() {
    ProfileScreen()
}
