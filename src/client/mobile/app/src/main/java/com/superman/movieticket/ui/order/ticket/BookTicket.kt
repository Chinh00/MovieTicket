package com.superman.movieticket.ui.order.ticket


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.superman.movieticket.domain.entities.Screening
import com.superman.movieticket.domain.entities.Seat
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.components.ScreenLoading
import com.superman.movieticket.ui.order.food.hooks.NavigateOrderFood
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.model.ReservationExtendModel
import com.superman.movieticket.ui.order.model.SeatReservationsCreateModel
import com.superman.movieticket.ui.order.ticket.components.ScreenShape
import com.superman.movieticket.ui.order.ticket.components.SeatComp
import com.superman.movieticket.ui.order.ticket.control.BookTicketViewModel
import com.superman.movieticket.ui.theme.CustomColor4
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
@AndroidEntryPoint
class TicketBookActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = { TicketActivityComp(
                Gson().fromJson(intent.getStringExtra("ReservationCreateModel"),ReservationCreateModel::class.java),
                intent.getStringExtra("roomId")!!
            ) }, title = "Chọn ghế ")
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketActivityComp(
    reservationCreateModel: ReservationCreateModel,
    roomId: String

) {
    val context = LocalContext.current
    val bookTicketViewModel: BookTicketViewModel = hiltViewModel()
    val reservationCreateModelState = remember {
        mutableStateOf(reservationCreateModel)
    }



    val selectedSeat = remember {

        mutableStateListOf<String>()
    }


    val colorText = Color.White


    val totalPrice = rememberSaveable {
        mutableStateOf(80000)

    }

    val seats = bookTicketViewModel.roomState.collectAsState()


    LaunchedEffect(key1 = Unit) {
        bookTicketViewModel.GetAllSeatsOfRoomAsync(roomId)
    }



    ScreenLoading(isLoading = bookTicketViewModel.apiState.collectAsState()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF060831))
        ) {


            val (s, e, b, t) = createRefs()


            val btn_bottom = createRef()

            Row(modifier = Modifier.constrainAs(e) {
                top.linkTo(parent.top)
            }) {


            }
            var scale by remember {
                mutableStateOf(1.5f)
            }
            var offSet by remember {
                mutableStateOf(Offset.Zero)
            }

            BoxWithConstraints(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(t) {
                    top.linkTo(e.bottom)
                    bottom.linkTo(b.top)

                }) {
                val state = rememberTransformableState { zoomChange, panChange, _ ->
                    scale = (scale * zoomChange).coerceIn(1f, 1.5f)
                    val extraWidth = (scale - 1) * constraints.maxWidth
                    val extraHeight = (scale - 1) * constraints.maxHeight
                    val maxX = extraWidth / 2
                    val maxY = extraHeight / 2

                    offSet = Offset(
                        x = (offSet.x + scale * panChange.x).coerceIn(-maxX, maxX),
                        y = (offSet.y + scale * panChange.y).coerceIn(-maxY, maxY)
                    )
                }


                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)

                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            translationX = offSet.x
                            translationY = offSet.y

                        }
                        .transformable(state), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        ScreenShape()
                    }
                    seats.value.seats.sortedBy { it.rowNumber.toString() }.groupBy { it.rowNumber }.forEach { i ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            i.value.sortedBy { it.colNumber.toString() }.forEach { j ->
                                SeatComp(
                                    seat = j,
                                    isEnable = true,
                                    isSelected = selectedSeat.contains(j.id),
                                    onClick = {
                                        if (selectedSeat.contains(j.id)) {
                                            selectedSeat.remove(j.id)
                                        } else
                                            selectedSeat.add(j.id)

                                    }
                                )

                                                    Spacer(modifier = Modifier.width(10.dp))
                            }

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Row(modifier = Modifier
                        .padding(vertical = 10.dp)

                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(10.dp)
                                    .background(Color.Gray, CircleShape)
                            )
                            Text(text = "Reserved", color = colorText)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(10.dp)
                                    .background(Color(0xFFEC9B24), CircleShape)
                            )
                            Text(text = "Selected", color = colorText)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(10.dp)
                                    .background(Color(0xFFFFFFFF), CircleShape)
                            )
                            Text(text = "Available", color = colorText)
                        }

                    }
                }
            }

            val t1 = createRef()




            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .constrainAs(t1) {
                        bottom.linkTo(btn_bottom.top)


                    }
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color(0xFF888888))
                    .padding(20.dp)
            ) {
                Column {
                    Text(text = "Giá tiền", fontSize = 18.sp, color = colorText)
                    Text(
                        text = "${totalPrice.value * selectedSeat.size}đ",
                        fontSize = 18.sp,
                        color = colorText
                    )
                }
                Column {
                    Text(text = "Ghế chọn", fontSize = 18.sp, color = colorText)

                    Row {

                        selectedSeat.forEach {
                            Column {
                                Row {
                                    val seat = bookTicketViewModel.roomState.collectAsState().value.seats.filter { t -> t.id == it }.first()
                                    Text(
                                        text = "${seat.rowNumber}${seat.colNumber}",
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(end = 5.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(
                                                Color(
                                                    0xB4000FFF
                                                )
                                            )
                                            .padding(5.dp),
                                        color = colorText
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .padding(5.dp)
                .constrainAs(btn_bottom) {
                    bottom.linkTo(parent.bottom)
                }) {

                CustomButton(
                    onClick = {
                        reservationCreateModelState.value.seatReservations.addAll(selectedSeat.map { SeatReservationsCreateModel(it) })
                        NavigateOrderFood(
                            context = context,
                            reservationCreateModel = reservationCreateModelState.value,
                            ReservationExtendModel(
                                (totalPrice.value * selectedSeat.size).toLong()
                            )
                        )

                    }, text = "Tiếp theo", modifier = Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxWidth(), MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }

}







