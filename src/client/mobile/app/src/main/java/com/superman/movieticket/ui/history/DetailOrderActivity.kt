package com.superman.movieticket.ui.history

//import com.superman.movieticket.ui.history.control.DetailOrderViewModelFactory
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.ui.history.control.DetailOrderViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailOrderActivity : ComponentActivity() {

    //    private val movieViewModel: DetailOrderViewModel by viewModels {
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                // Create Retrofit instance
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://yourapi.com/") // Thay thế bằng URL cơ sở thực tế của bạn
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//                val apiService = retrofit.create(MovieService::class.java)
//
//                return TODO("Provide the return value")
//            }
//
//        }
//    }
//
    val detailOrderViewModel :DetailOrderViewModel by viewModels()


    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val name = intent.getStringExtra("movieName")
            val orderDate = intent.getStringExtra("orderDate")
            val date = intent.getStringExtra("date")
            val total = intent.getDoubleExtra("total", 0.0)
            val quantity = intent.getIntExtra("quantity", 0)
            val imageResId = intent.getIntExtra("imageResId", 0)
            val servicename = intent.getStringExtra("serviceName")
            val servicePrice = intent.getDoubleExtra("servicePrice", 0.0)
            val serviceQuantity = intent.getIntExtra("serviceQuantity", 0)
            val ticketPrice = intent.getDoubleExtra("ticketPrice", 0.0)
            val theaterName = intent.getStringExtra("theaterName")
            val seatNumber = intent.getStringExtra("seatNumber")

            DetailScreen(name, formatOrderDate(orderDate), date, quantity, total, imageResId, servicename, servicePrice, serviceQuantity, ticketPrice, theaterName, seatNumber)

            lifecycleScope.launch {
                detailOrderViewModel.listOrderReservation.collect { reservations ->
                    // Cập nhật UI khi danh sách phim đã đặt thay đổi
                    Log.i("DetailOrderViewModel", reservations.toString())
                    updateUI(reservations)
                }
            }
        }



        // Gọi API để lấy danh sách phim đã đặt
    }

    private fun formatOrderDate(orderDate: String?): String? {
        return if (orderDate != null) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy/MM/dd hh:mm a", Locale.getDefault())
            val date = inputFormat.parse(orderDate)
            date?.let { outputFormat.format(it) }
        } else {
            null
        }
    }

    private fun updateUI(movies: List<Reservation>) {
        // Cập nhật UI của bạn ở đây
    }
}

@Composable
fun DetailScreen(
    movieName: String? = "",
    orderDate: String? = "",
    date: String? = "",
    quantity: Int = 0,
    totalPrice: Double = 0.0,
    imageResId: Int = 0,
    serviceName: String? = "",
    servicePrice: Double = 0.0,
    serviceQuantity: Int = 0,
    ticketPrice: Double = 0.0,
    theaterName: String? = "",
    seatNumber: String? = ""
) {
    val context = LocalContext.current
    val totalCost = totalPrice + servicePrice

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(12f / 5f)
                    .padding(horizontal = 15.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(30.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("CGV VinCom", color = Color.Black, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text("Ngày đặt: $orderDate", color = Color.Black, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("$movieName", color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(date ?: "", color = Color.Black, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rạp",
                        color = Color.Gray,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Số ghế",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("$theaterName", color = Color.Black, fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("$seatNumber", color = Color.Black, fontSize = 20.sp, modifier = Modifier.padding(end = 16.dp))
                }
            }

            Spacer(modifier = Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Chi tiết giao dịch",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "id:23048W",
                            color = Color.Gray,
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
                                color = Color.Gray,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Visa",
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Success",
                                    color = Color.Gray,
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 130.dp)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                                    contentDescription = "Success",
                                    tint = Color.Black,
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
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.priceticket),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$ticketPrice",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.amountticket),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = quantity.toString(),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$serviceName",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$servicePrice",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.amountservice),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = serviceQuantity.toString(),
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.sum),
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$totalCost",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(3.dp))
            Image(
                painter = painterResource(id = R.drawable.img_1),
                contentDescription = "Ma Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DetailScreenPreview() {
    DetailScreen(
        movieName = "Avengers: Endgame",
        orderDate = "2024/08/05 09:45 PM",
        date = "2024/06/05 12:30 - 2024/06/05 14:00",
        theaterName = "Cinema 8",
        seatNumber = " J-12",
        serviceName = "Bắp khổng lồ",
        quantity = 2,
        totalPrice = 25.0,
        imageResId = R.drawable.kingkong2024,
        servicePrice = 5.0,
        serviceQuantity = 1,
        ticketPrice = 12.5
    )
}
