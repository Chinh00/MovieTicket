package com.superman.movieticket.ui.order.screening

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.components.ScreenLoading
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.screening.control.ScreenActivityViewModel
import com.superman.movieticket.ui.order.ticket.control.BookTicketViewModel
import com.superman.movieticket.ui.order.ticket.hooks.NavigateBookTicket
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@AndroidEntryPoint
class ScreenActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                ScreensComp(intent.getStringExtra("movieId")!!) {
                    finish()
                }
            }, title = "Chọn phòng ", onNavigateUp = {
                finish()
            })
        }

    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreensComp(
    movieId: String,
    finish: () -> Unit
) {
    val dateScrollState = rememberScrollState()
    val context = LocalContext.current

    val screenViewModel: ScreenActivityViewModel = hiltViewModel()
    val screenings = screenViewModel.listRoom.collectAsState()
    val today = LocalDate.now()
    val selectedDate = remember {
        mutableStateOf<LocalDate?>(today)
    }



    LaunchedEffect(key1 = Unit) {
        screenViewModel.HandleGetRoomWithMovieAndDate(movieId, LocalDateTime.of(today, LocalTime.MIDNIGHT), LocalDateTime.of(today.plusDays(1), LocalTime.MIDNIGHT))
    }


    Column {
        Row(
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp)

                .horizontalScroll(dateScrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (0..7).forEach { i ->
                val date = today.plusDays(i.toLong())
                DateComp(date = date, isSelected = selectedDate.value == date) {
                    selectedDate.value = it
                    screenViewModel.HandleGetRoomWithMovieAndDate(movieId, LocalDateTime.of(selectedDate.value, LocalTime.MIDNIGHT), LocalDateTime.of(selectedDate.value?.plusDays(1), LocalTime.MIDNIGHT))

                }
            }
        }

        Box (modifier = Modifier
            .fillMaxSize()
            .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.TopCenter
        ) {
            ScreenLoading(isLoading = screenViewModel.apiState.collectAsState()) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val targetFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val nowPlus15Minutes = LocalDateTime.now().plusMinutes(15)

                    val filteredScreenings = screenings.value.sortedBy { it.startDate }


                        .filter {


                            val now = LocalDateTime.now().toString()
                            val target = LocalDateTime.parse(it.startDate, DateTimeFormatter.ISO_DATE_TIME).toString()



                            Log.d("Chinh targetTime", LocalDateTime.parse(it.startDate, DateTimeFormatter.ISO_DATE_TIME).toString())
                            Log.d("now", LocalDateTime.now().toString())

                            now < target

                        }
                    itemsIndexed(filteredScreenings) { index, item ->
                        ScreenItemComp(item)
                    }
                }
            }

        }


    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenItemComp(
    screening: Screening,
) {
    val context = LocalContext.current
    val bookTicketViewModel: BookTicketViewModel = hiltViewModel()
    val reservationCreateModel = remember {
        mutableStateOf(ReservationCreateModel(
            screeningId = screening.id,
            seatReservations = mutableListOf(),
            serviceReservations = mutableListOf(),
            ))
    }

    val count = remember {
        mutableStateOf(0)
    }
    LaunchedEffect(screening.roomId, screening.id) {
        bookTicketViewModel.GetAllSeatsOfRoomAsyncWithCallBack(screening.roomId, screening.id) {
            count.value = it.seats.count { it.isPlaced }
        }
    }



    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .shadow(4.dp, spotColor = Color.Green)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                NavigateBookTicket(
                    context, reservationCreateModel.value, screening.roomId
                )
            }
            .background(MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .height(135.dp)
                .width(95.dp)
                .padding(5.dp),
            verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Screen ${screening.room.roomNumber}".uppercase(),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )


            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val target = LocalDateTime.parse(screening.startDate, DateTimeFormatter.ISO_DATE_TIME).toString()
                    val targetEnd = LocalDateTime.parse(screening.startDate, DateTimeFormatter.ISO_DATE_TIME).plusMinutes(100).toString()
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

                    val localDateTimeStart = LocalDateTime.parse(target, formatter)
                    val localDateTimeEnd = LocalDateTime.parse(targetEnd, formatter)

                    val hourStart = localDateTimeStart.hour
                    val minuteStart = localDateTimeStart.minute


                    val hourEnd = localDateTimeEnd.hour
                    val minuteEnd = localDateTimeEnd.minute
                    Text(
                        text = "${hourStart} : ${minuteStart} - ${hourEnd} : ${minuteEnd}",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(text = "${count.value}/${screening.room.seats.size}", color = MaterialTheme.colorScheme.background)

            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateComp(
    date: LocalDate, isSelected: Boolean = false,
    onClick: (LocalDate) -> Unit = {}
) {
    val color = when {
        isSelected -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.background
    }
    val textBg = when {
        isSelected -> MaterialTheme.colorScheme.background
        else ->  MaterialTheme.colorScheme.onBackground
    }

    Surface(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(date) }, shape = RoundedCornerShape(8.dp), color = color
    ) {
        Column(
            modifier = Modifier.padding(15.dp,5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            val ngaytrongtuan = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            Text(
                text ="${ngaytrongtuan}" ,
                style = MaterialTheme.typography.titleMedium,color=textBg
            )
            val ngaytrongthang = date.dayOfMonth
            val ntt =  if(ngaytrongthang>=10) ngaytrongthang.toString() else "0$ngaytrongthang"

            Text(text = ntt,color=textBg, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)


        }
    }
}