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
import java.text.SimpleDateFormat
import java.util.*
import com.superman.movieticket.R

data class Order(
    val movieName: String,
    val orderDate: String,
    val orderDateTime: Date,
    val showStartTime: String,
    val showEndTime: String,
    val showStartDateTime: Date,
    val showEndDateTime: Date,
    val ticketPrice: Double,
    val quantity: Int,
    val total: Double,
    val imageResId: Int,
    val serviceName: String,
    val servicePrice: Double,
    val serviceQuantity: Int,
    val theaterName: String,
    val seatNumber: String
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
            .background(Color.White)
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
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
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
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = painterResource(id = order.imageResId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Phim: ${order.movieName}", color = Color.Black)
                    Text("Ngày đặt: ${order.orderDate}", color = Color.Black)
                }
                Button(
                    onClick = { navigateToDetail(context, order) },
                    modifier = Modifier
                        .padding(start = 20.dp, top = 50.dp)
                ) {
                    Text("Chi tiết")
                }
            }
        }
    }
}

private fun fetchOrderHistory(): List<Order> {
    return listOf(
        Order(
            "Avengers: Endgame",
            "2024/08/05 23:45 PM",
            parseDateTime("2024/08/05 23:45 PM"),
            "12:30 PM",
            "3:30 PM",
            parseDateTime("2024/06/05 12:30 PM"),
            parseDateTime("2024/06/05 3:30 PM"),
            150.0,
            2,
            300.0,
            R.drawable.kingkong2024,
            "Snack vị tảo biển",
            5.0,
            1,
            "Cinema 8",
            "A-16"
        ),
        Order(
            "Inception",
            "2024/05/06 13:24 AM",
            parseDateTime("2024/05/06 13:24 AM"),
            "01:30 PM",
            "03:00 PM",
            parseDateTime("2024/06/06 01:30 PM"),
            parseDateTime("2024/06/06 03:00 PM"),
            120.0,
            1,
            120.0,
            R.drawable.bietdoidanhthue,
            "Bimbim khoai tây",
            10.0,
            2,
            "Cinema 9",
            "B-24"
        ),
        Order(
            "Conan",
            "2024/06/07 14:30 PM ",
            parseDateTime("2024/06/07 14:30 PM"),
            "03:00 PM",
            "05:00 PM",
            parseDateTime("2024/06/07 03:00 PM"),
            parseDateTime("2024/06/07 05:00 PM"),
            90.0,
            1,
            90.0,
            R.drawable.conan,
            "Pepsi",
            15.0,
            1,
            "Cinema 3",
            "B-24"
        ),
        Order(
            "Doremon",
            "2024/09/05 11:20 PM",
            parseDateTime("2024/09/05 11:20 PM"),
            "11:30 AM",
            "01:00 PM",
            parseDateTime("2024/09/07 11:30 AM"),
            parseDateTime("2024/09/07 01:00 PM"),
            90.0,
            1,
            90.0,
            R.drawable.doremon,
            "Snack vị tảo biển",
            5.0,
            1,
            "Cinema 5",
            "A-16"
        ),
        Order(
            "Naruto",
            "2023/09/01 13:23 PM",
            parseDateTime("2023/09/01 13:23 PM"),
            "10:00 AM",
            "12:00 PM",
            parseDateTime("2023/09/07 10:00 AM"),
            parseDateTime("2023/09/07 12:00 PM"),
            190.0,
            2,
            380.0,
            R.drawable.naruto,
            "Bimbim ngô",
            10.0,
            2,
            "Cinema 5",
            "A-18"
        ),
        Order(
            "Spider-Man: No Way Home",
            "2023/12/14 12:56 PM",
            parseDateTime("2023/12/14 12:56 PM"),
            "08:30 PM",
            "10:00 PM",
            parseDateTime("2023/12/17 08:30 PM"),
            parseDateTime("2023/12/17 10:00 PM"),
            120.0,
            1,
            120.0,
            R.drawable.poster,
            "Pepsi vị chanh ko calo",
            15.0,
            1,
            "Cinema 5",
            "A-16"
        ),
        Order(
            "Toy Story 4",
            "2024/07/09 21:56 AM",
            parseDateTime("2024/07/09 21:56 AM"),
            "02:15 PM",
            "04:00 PM",
            parseDateTime("2024/07/10 02:15 PM"),
            parseDateTime("2024/07/10 04:00 PM"),
            190.0,
            2,
            380.0,
            R.drawable.latmat6,
            "Snack",
            5.0,
            2,
            "Cinema 3",
            "C-26"
        ),
        Order(
            "Joker",
            "2024/08/02 15:23 PM",
            parseDateTime("2024/08/02 15:23 PM"),
            "07:45 PM",
            "09:00 PM",
            parseDateTime("2024/08/05 07:45 PM"),
            parseDateTime("2024/08/05 09:00 PM"),
            120.0,
            1,
            120.0,
            R.drawable.coba,
            "Bimbim",
            10.0,
            1,
            "Cinema 7",
            "D-16"
        ),
        Order(
            "Frozen II",
            "2024/11/22 16:24 AM",
            parseDateTime("2024/11/22 16:24 AM"),
            "01:00 PM",
            "02:30 PM",
            parseDateTime("2024/11/22 01:00 PM"),
            parseDateTime("2024/11/22 02:30 PM"),
            120.0,
            1,
            120.0,
            R.drawable.exhuma,
            "Snack",
            5.0,
            1,
            "Cinema 3",
            "H-16"
        ),
        Order(
            "Black Panther",
            "2024/02/15 21:34 PM",
            parseDateTime("2024/02/15 21:34 PM"),
            "05:00 PM",
            "07:00 PM",
            parseDateTime("2024/02/16 05:00 PM"),
            parseDateTime("2024/02/16 07:00 PM"),
            120.0,
            2,
            380.0,
            R.drawable.vang,
            "Pepsi",
            15.0,
            1,
            "Cinema 2",
            "G-16"
        ),
    )
}

private fun navigateToDetail(context: Context, order: Order) {
    val intent = Intent(context, DetailOrderActivity::class.java)
    intent.putExtra("movieName", order.movieName)
    intent.putExtra("orderDate", formatDateTime(order.orderDateTime)) // Sử dụng formatDateTime với orderDateTime
    intent.putExtra("date", "${formatDateTime(order.showStartDateTime)} - ${formatDateTime(order.showEndDateTime)}")
    intent.putExtra("total", order.total)
    intent.putExtra("quantity", order.quantity)
    intent.putExtra("imageResId", order.imageResId)
    intent.putExtra("serviceName", order.serviceName)
    intent.putExtra("servicePrice", order.servicePrice)
    intent.putExtra("serviceQuantity", order.serviceQuantity)
    intent.putExtra("ticketPrice", order.ticketPrice)
    intent.putExtra("theaterName", order.theaterName) // Thêm thông tin theaterName
    intent.putExtra("seatNumber", order.seatNumber) // Thêm thông tin seatNumber
    context.startActivity(intent)
}

@Composable
@Preview(showSystemUi = true)
fun MyOrderActivityPre() {
    MyOrderActivityScreen()
}

private fun parseDateTime(dateTimeString: String): Date {
    val formatter = SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault())
    return formatter.parse(dateTimeString) ?: Date()
}

@SuppressLint("SimpleDateFormat")
private fun formatDateTime(dateTime: String): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault())
    return formatter.format(dateTime)
}

private fun formatDateTime(dateTime: Date): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
    return formatter.format(dateTime)
}
