package com.superman.movieticket.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superman.movieticket.R



class DetailOrderActivity :ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val name = intent.getStringExtra("movieName")
            val date= intent.getStringExtra("date")
            val total = intent.getDoubleExtra("total",0.0)
            val quantity = intent.getIntExtra("quantity",0)
            val duration = intent.getIntExtra("duration",0)
            val imageResId = intent.getIntExtra("imageResId",0)
            DetailScreen(name,date, duration ,quantity ,total, imageResId)
        }
    }
}

@Composable
fun DetailScreen(
    movieName: String?="",
    date: String?="",
    duration: Int?=0,
    quantity: Int?=0,
    totalPrice: Double?=0.0,
    imageResId: Int?=0
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Image(
                    painter = painterResource(id = imageResId!!),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("Movie: $movieName", color = Color.LightGray, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Date: $date", color = Color.LightGray, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Duration: $duration minutes", color = Color.LightGray, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Price: $totalPrice", color = Color.LightGray, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Transaction details",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "id:23048W",
                            color = Color.LightGray,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(50.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(5.dp, Color.Transparent),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.visa),
                                contentDescription = "Visa Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "Edit Cart",
                                color = Color.LightGray,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Visa",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
//
                                Text(
                                    text = "Success",
                                    color = Color.LightGray,
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 115.dp)
                                )

                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                    contentDescription = "Success",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                        .padding(start = 3.dp)
                                )
                            }

                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price of one ticket",
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f)) // Sử dụng Spacer để căn chỉnh Text và giá tiền
                Text(
                    text = "$totalPrice",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp, top = 10.dp)
                )
                
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quantity",
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f)) // Sử dụng Spacer để căn chỉnh Text và giá tiền
                Text(
                    text = quantity.toString(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp, top = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    color = Color.LightGray,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.weight(1f)) // Sử dụng Spacer để căn chỉnh Text và giá tiền
                Text(
                    text = "$totalPrice",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp, top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Ma Image",
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DetailScreenPreview() {
    DetailScreen("Avengers: Endgame", "2024-06-05", 180, 2,25.0, R.drawable.kingkong2024)
}
