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
                Gson().fromJson(intent.getStringExtra("screening"),Screening::class.java)
            ) }, title = "Chọn ghế ")
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketActivityComp(
    screening: Screening

) {
    val bookTicketViewModel: BookTicketViewModel = hiltViewModel()

    val context = LocalContext.current
    val selectedSeat = remember {

        mutableStateListOf<String>()
    }
    val bookedSeats = listOf("F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8")
    val today = LocalDate.now()
    val dateScrollState = rememberScrollState()
    val timeScrollState = rememberScrollState()

    val selectedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val selectedTime = remember {
        mutableStateOf<String?>(null)
    }
    val colorText = Color.White
    val totalPrice = rememberSaveable {
        mutableStateOf(180)

    }

    LaunchedEffect(key1 = Unit) {
        bookTicketViewModel.GetAllSeatsOfRoomAsync(screening.roomId)
    }



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
                /*seats.value.seats.sortedBy { it.rowNumber.toString() }.groupBy { it.rowNumber }.forEach { i ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        i.value.sortedBy { it.colNumber.toString() }.forEach { j ->
                            val seatNumber = "${(64 + j.rowNumber.toInt()).toChar()}$j"
                            val isBooked = bookedSeats.contains(seatNumber)
                          *//*  if (j == 1) {
                                Row {
                                    Text(
                                        text = "${(64 + i).toChar()}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )
                                }
                            }*//*
                            SeatComp(
                                seatNumber = seatNumber,
                                isEnable = !isBooked,
                                isSelected = selectedSeat.contains(seatNumber)
                            ) { selected, seat ->
                                if (selected) selectedSeat.remove(seat)
                                else selectedSeat.add(seat)
                            }
                           *//* if (j == 8) {
                                Row {
                                    Text(
                                        text = "${(64 + i).toChar()}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.background,
                                        modifier = Modifier.padding(start = 15.dp)
                                    )
                                }
                            }*//*
                            //if (j != 8) Spacer(modifier = Modifier.width(if (j == 4) 25.dp else 8.dp))

                        }

                    }
                    //Spacer(modifier = Modifier.height(if (i != 6) 8.dp else 25.dp))

                }*/
            }
        }

        val t1 = createRef()


        Row(modifier = Modifier
            .padding(vertical = 10.dp)
            .constrainAs(b) {
                bottom.linkTo(t1.top)

            }
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
                Text(text = "Total", fontSize = 18.sp, color = colorText)
                Text(
                    text = "${totalPrice.value * selectedSeat.size}đ",
                    fontSize = 18.sp,
                    color = colorText
                )
            }
            Column {
                Text(text = "Selected Seats", fontSize = 18.sp, color = colorText)

                Row {

                    selectedSeat.forEach {
                        Column {
                            Row {
                                Text(
                                    text = "$it",
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
                onClick = { /*TODO*/ }, text = "PAYMENT TICKET", modifier = Modifier
                    .padding(bottom = 15.dp)
                    .fillMaxWidth(), CustomColor4
            )
        }
    }

}


@Composable
fun TimeComp(
    time: String, isSelected: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    val color = when {
        isSelected -> Color.Yellow
        else -> Color.Yellow.copy(0.5f)
    }
    val textBg = when {
        isSelected -> Color.White
        else -> Color.Transparent
    }

    Surface(
        modifier = Modifier
            .wrapContentSize()
            .clip(RectangleShape)
            .clickable { onClick(time) }, color = color
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color.Black, CircleShape)
            )
            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(12.dp)
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color.Black, CircleShape)
            )

        }
    }
}



@Composable
@Preview(showBackground = true)
fun TicketActivityScreenPre() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth()
                .height(30.dp)
                .shadow(8.dp, shape = RectangleShape)
                .background(Color.Red)

        ) {
            Text(
                text = "Screen",
                color = Color.White,
                modifier = Modifier.align(alignment = Alignment.Center),
                fontSize = 22.sp
            )

        }
    }
}

@Composable
fun SeatComp(
    isEnable: Boolean = false,
    isSelected: Boolean = false,
    seatNumber: String,
    onClick: (Boolean, String) -> Unit = { _, _ -> },
    //seat: Seat
) {
    val seatColor = when {
        !isEnable -> Color.Gray
        isSelected -> Color(0xFFAF6D0C)
        else -> Color.White
    }
    val textColor = when {
        isSelected -> Color.White
        else -> MaterialTheme.colorScheme.onBackground
    }
    Box(modifier = Modifier
        .size(32.dp)
        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
        .clip(
            RoundedCornerShape(8.dp)
        )
        .background(seatColor)
        .clickable(enabled = isEnable) { onClick(isSelected, seatNumber) }
        .padding(5.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(text = seatNumber, style = MaterialTheme.typography.bodyMedium.copy(color = textColor))
    }
}

@Composable
fun ScreenShape() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DrawScreenCurve()
    }
}

@Composable
fun DrawScreenCurve() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val width = size.width
        val height = size.height

        // Gradient brush cho bóng mờ dần
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color.Gray.copy(alpha = 0.5f),  // Màu bóng bắt đầu với độ mờ
                Color.Transparent               // Kết thúc trong suốt
            ),
            startY = 0f,
            endY = height
        )

        val shadowPath = Path().apply {
            moveTo(0f, height * 0.8f)  // Bắt đầu từ điểm bên trái dưới
            cubicTo(
                width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                width, height * 0.8f   // Điểm cuối cùng bên phải
            )
        }
        val shadowPath1 = Path().apply {
            moveTo(0f, height * 1f)  // Bắt đầu từ điểm bên trái dưới
            cubicTo(
                width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                width, height * 1f   // Điểm cuối cùng bên phải
            )
        }

        // Vẽ đường cong đổ bóng với gradient
        drawPath(
            path = shadowPath,
            brush = gradientBrush,
            style = Stroke(width = 10.dp.toPx())
        )
        drawPath(
            path = shadowPath,
            brush = gradientBrush,
            style = Stroke(width = 10.dp.toPx())
        )

        // Vẽ đường cong chính
        val mainPath = Path().apply {
            moveTo(0f, height * 0.6f)  // Bắt đầu từ điểm bên trái dưới
            cubicTo(
                width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                width, height * 0.6f   // Điểm cuối cùng bên phải
            )
        }

        drawPath(
            path = mainPath,
            color = Color.Gray,
            style = Stroke(width = 4.dp.toPx())
        )
    }
}
