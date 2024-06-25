package com.superman.movieticket.ui.history

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.Reservation
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.history.control.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MyOrderActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                MyOrderComp()
            }, title = "Lịch sử đặt vé", onNavigateUp = {
                finish()
            })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyOrderComp() {
    val reservationViewModel: ReservationViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(reservationViewModel.fetchReservedMovies()) {
        reservationViewModel.fetchReservedMovies()
    }
    Toast.makeText(
        context,
        reservationViewModel.listOrderReservation.collectAsState().value.toString(),
        Toast.LENGTH_SHORT
    ).show()

    if (reservationViewModel.listOrderReservation.collectAsState().value.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            items(reservationViewModel.listOrderReservation.value) { r ->
                OrderItem(reservation = r)
            }
        }
    } else {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {

            Text("Bạn hãy đặt vé",color=MaterialTheme.colorScheme.onBackground)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderItem(reservation: Reservation) {
    val context = LocalContext.current
    val colorText = MaterialTheme.colorScheme.onBackground
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp).clickable { navigateToDetail(context, reservation) },
        colors = CardDefaults.cardColors(containerColor = Color.LightGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            AppOptions.BASE_URL + "/admin-api/image/" +reservation.screening?.movie?.avatar?:"", error = painterResource(
                                id = R.drawable.error_img
                            )
                        ),
                        contentDescription = reservation.screening.movie.name?:"Không có dữ liệu",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column() {
                    Text("Phim: ${reservation.screening?.movie?.name?:"Không có dữ liệu"}", color = colorText)
                    Text(
                        "Ngày đặt: ${
                            DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                                reservation.createdDate.toString(),
                                "dd/MM/yyyy"
                            )
                        }", color = colorText
                    )
                }
//                Button(
//                    colors = ButtonDefaults.buttonColors(
//
//                        contentColor = MaterialTheme.colorScheme.background,
//                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer
//                    ),
//                    onClick = { navigateToDetail(context, reservation) },
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(10.dp))
//                        .padding(start = 20.dp, top = 50.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Color.Black,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                ) {
//                    Text(context.getString(R.string.txt_detail))
//                }
            }
        }
    }
}


private fun navigateToDetail(context: Context, reservation: Reservation) {
    val intent = Intent(context, DetailOrderActivity::class.java)
    intent.putExtra("reservation", reservation)
    context.startActivity(intent)
}

//@Composable
//@Preview(showSystemUi = true)
//fun MyOrderActivityPre() {
//    MyOrderComp()
//}


