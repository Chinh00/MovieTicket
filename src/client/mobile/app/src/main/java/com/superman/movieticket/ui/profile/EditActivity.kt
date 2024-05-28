package com.superman.movieticket.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.ui.home.model.Movie
import com.superman.movieticket.ui.home.model.listMovies

@Composable
fun EditActivity() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            LazyColumn {
                item {
                    Avatar(listMovies.first())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            // Thêm phần thanh ngang
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(20.dp))
            // Thêm OutlinedTextField
            OutlinedTextField(
                value = "",
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
                placeholder = {
                    Text(
                        text = "Full name",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                )

            )

            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Xử lý thay đổi giá trị */ },
                    modifier = Modifier.weight(1f).border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)), // Đặt trọng số 1 cho TextField đầu tiên
                    placeholder = {
                        Text(
                            text = "Gender",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    },
                    shape = RoundedCornerShape(16.dp), // Bo tròn với bán kính 16.dp
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent, // Màu của vùng nội bộ của góc bo tròn
                    )
                )

                Spacer(modifier = Modifier.width(10.dp)) // Khoảng cách giữa hai TextField
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Xử lý thay đổi giá trị */ },
                    modifier = Modifier.weight(1f).border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)), // Đặt trọng số 1 cho TextField thứ hai
                    placeholder = {
                        Text(
                            text = "Birthday",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    },
                    shape = RoundedCornerShape(16.dp), // Bo tròn với bán kính 16.dp
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Transparent, // Màu của vùng nội bộ của góc bo tròn
                    )
                )

            }


            Spacer(modifier = Modifier.height(20.dp))
            // Thêm OutlinedTextField
            OutlinedTextField(
                value = "",
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
                placeholder = {
                    Text(
                        text = "Number",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                )

            )

            Spacer(modifier = Modifier.height(20.dp))
            // Thêm OutlinedTextField
            OutlinedTextField(
                value = "",
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth().border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                )

            )

            Spacer(modifier = Modifier.height(20.dp))
            // Thêm OutlinedTextField
            OutlinedTextField(
                value = "",
                onValueChange = { /* Do nothing */ },
                modifier = Modifier
                    .fillMaxWidth().border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
                placeholder = {
                    Text(
                        text = "User name",
                        color = Color.LightGray,
                        fontSize = 16.sp
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                )

            )

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { /* Xử lý khi nút được nhấn */ },
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
            ) {
                Text(text = "SAVE")
            }

        }
    }
}

@Composable
fun Avatar(movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val image = createRef()

            Image(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(CircleShape)
                    .size(100.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null
            )
        }
    }
    Text(
        text = "Dong Chinh Khanh",
        color = Color.White,
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
        color = Color.White,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
    )
}


@Composable
@Preview(showSystemUi = true)
fun EditActivityPre() {
    EditActivity()
}