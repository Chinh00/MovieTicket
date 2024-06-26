package com.superman.movieticket.ui.history

//import com.superman.movieticket.ui.history.control.DetailOrderViewModelFactory
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.history.control.ReservationViewModel
import com.superman.movieticket.ui.theme.MyAppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DetailOrderActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BaseScreen(content = {
                MyAppTheme {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getSerializableExtra("reservation",Reservation::class.java)
                            ?.let { DetailScreen(it) }
                    }

                }
            }, title = "Chi tiết vé đặt", onNavigateUp = {
                finish()
            })

        }


        // Gọi API để lấy danh sách phim đã đặt
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    reservation: Reservation
) {
    val context = LocalContext.current


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
                    painter = rememberAsyncImagePainter(AppOptions.BASE_URL + "/admin-api/image/" +reservation.screening.movie.avatar),
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
                Text("Ngày đặt: ${DatetimeHelper.ConvertISODatetimeToLocalDatetime(reservation.createdDate.toString(),"dd/MM/yyyy")}", color = Color.Black, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "${reservation.screening?.movie?.name?: "Khong co"}",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Thời gian: ${DatetimeHelper.ConvertISODatetimeToLocalDatetime(reservation.createdDate.toString(),"HH:mm")}" ?: "", color = Color.Black, fontSize = 20.sp)
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
                    Text("${reservation.screening.room.roomNumber}", color = Color.Black, fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    var nameSeat = ""
                    reservation.seatReservations.forEach {s->  nameSeat = "${s.seat?.rowNumber}${s.seat?.colNumber}" }
                    Text(
                        nameSeat,
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
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
                                    modifier = Modifier
                                        .size(20.dp)
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
                    text = "${reservation.itemPrice}",
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
                    text = "${reservation.seatReservations.size}",
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
                    text = "${reservation.serviceReservations.forEach { it.service.name }}",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${reservation.serviceReservations.forEach { it.price+it.quantity }}",
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
                    text = "${reservation.serviceReservations.size}",
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
                    text = "$",
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

