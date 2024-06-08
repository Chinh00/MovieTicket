package com.superman.movieticket.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.superman.movieticket.R

data class Order(
    val movieName: String,
    val date: String,
    val duration: Int,
    val quantity: Int,
    val total: Int,
    val imageResId: Int
)

class MyOrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyOrderActivityScreen()
        }
    }
}

@Composable
fun MyOrderActivityScreen() {
    val orders = fetchOrderHistory()
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        items(orders) { order ->
            OrderItem(order = order, context = context)
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OrderItem(order: Order, context: Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = order.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Movie: ${order.movieName}", color = Color.White)
                    Text("Date: ${order.date}", color = Color.White)
                    Text("Duration: ${order.duration} minutes", color = Color.White)
                }
                Button(
                    onClick = { navigateToDetail(context, order) },
                    modifier = Modifier
                        .padding(start = 10.dp, top = 50.dp)
                ) {
                    Text("Detail")
                }
            }
        }
    }
}

private fun fetchOrderHistory(): List<Order> {
    return listOf(
        Order("Avengers: Endgame", "2024-06-05", 180,2, 150, R.drawable.kingkong2024),
        Order("Inception", "2024-06-06", 150,1,120, R.drawable.bietdoidanhthue),
        Order("Conan", "2024-06-07", 152,1,90, R.drawable.conan),
        Order("Doremon", "2024-09-07", 132, 1,90, R.drawable.doremon),
        Order("Naruto", "2023-09-07", 132,2,180, R.drawable.naruto),
        // Add more orders to demonstrate scrolling
        Order("Spider-Man: No Way Home", "2023-12-17", 148,1,120, R.drawable.poster),
        Order("Toy Story 4", "2024-07-10", 100,2,180, R.drawable.latmat6),
        Order("Joker", "2024-08-05", 122, 1,120, R.drawable.coba),
        Order("Frozen II", "2024-11-22", 103,1,120, R.drawable.exhuma),
        Order("Black Panther", "2024-02-16", 134,2,180, R.drawable.vang),
        Order("Aquaman", "2024-12-21", 143, 1,100,R.drawable.kungfu)
    )
}

@Composable
@Preview(showSystemUi = true)
fun MyOrderActivityPre() {
    MyOrderActivityScreen()
}

private fun navigateToDetail(context: Context, order: Order) {
    val intent = Intent(context, DetailOrderActivity::class.java)
    intent.putExtra("movieName", order.movieName)
    intent.putExtra("date", order.date)
    intent.putExtra("duration", order.duration)
    intent.putExtra("quantity", order.quantity)
    intent.putExtra("total", order.total.toDouble())
    intent.putExtra("imageResId", order.imageResId)
    context.startActivity(intent)
}
