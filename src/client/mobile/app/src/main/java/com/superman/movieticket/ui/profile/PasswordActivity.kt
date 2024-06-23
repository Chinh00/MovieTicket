package com.superman.movieticket.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.superman.movieticket.R

@Composable
fun PasswordActivity() {
    val context = LocalContext.current

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
                    painter = painterResource(id = R.drawable.avatar), // Thay đổi ID ảnh tương ứng
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "Dong Chinh Khanh",
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
                text = "@123promax",
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

            }
            Spacer(modifier = Modifier.height(20.dp))

            var currentpasswordText by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = currentpasswordText, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    onValueChange = { currentpasswordText = it },
                    placeholder = { Text("Current Password", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            var newpasswordText by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = newpasswordText, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    onValueChange = { newpasswordText = it },
                    placeholder = { Text("New Password", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            var confirmText by remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(13.dp))
                    .padding(1.dp) // Padding nhỏ để tránh nội dung bị cắt
            ) {
                TextField(
                    value = confirmText, colors = TextFieldDefaults.colors(
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.LightGray
                    ),
                    onValueChange = { confirmText = it },
                    placeholder = { Text("Confirm Password", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .clip(RoundedCornerShape(13.dp)),
                )
            }


            Spacer(modifier = Modifier.height(210.dp))
            Button(
                onClick = { /* Xử lý khi nút được nhấn */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(start = 5.dp, end = 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, // Màu nền của nút
                ),
                shape = RoundedCornerShape(10.dp) // Hình dạng của nút
            ) {
                Text(text = "SAVE")
            }
        }
    }
}
@Composable
@Preview(showSystemUi = true)
fun PasswordActivityPre() {
    PasswordActivity()
}
