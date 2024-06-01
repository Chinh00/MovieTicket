package com.superman.movieticket.ui.order.ticket


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.ui.components.CustomButton
import java.time.LocalDate

class TicketBookActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                TicketActivityComp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TicketActivityComp() {
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
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060831))
    ) {


        val (s, e, b, t) = createRefs()


//        val timeCre = createRef()
//
//
//        Row(modifier = Modifier
//            .padding(top = 5.dp, start = 5.dp)
//            .constrainAs(timeCre) {
//                start.linkTo(parent.start)
//                end.linkTo(parent.end)
//                top.linkTo(dateCre.bottom)
//            }
//            .horizontalScroll(timeScrollState),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            (10..22 step 2).forEach { i ->
//                val time = "$i:00"
//                TimeComp(time = time, isSelected = selectedTime.value == time) {
//                    selectedTime.value = it
//                }
//            }
//        }
        val btn_bottom = createRef()

        Row(modifier = Modifier.constrainAs(e) {
            top.linkTo(parent.top)
        }) {

            ScreenShape()
        }
        Column(modifier = Modifier
            .padding(vertical = 10.dp)
            .constrainAs(t) {
                top.linkTo(e.bottom)

            }
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            //Seat Mapping
            (1..11).forEach { i ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    (1..8).forEach { j ->
                        val seatNumber = "${(64 + i).toChar()}$j"
                        val isBooked = bookedSeats.contains(seatNumber)
                        if ( j == 1) {
                            Row {
                                Text(
                                    text = "${(64 + i).toChar()}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.background,modifier=Modifier.padding(end = 15.dp)
                                )
                            }
                        }
                        SeatComp(
                            seatNumber = seatNumber,
                            isEnable = !isBooked,
                            isSelected = selectedSeat.contains(seatNumber)
                        ) { selected, seat ->
                            if (selected) selectedSeat.remove(seat)
                            else selectedSeat.add(seat)
                        }
                        if ( j == 8) {
                            Row {
                                Text(
                                    text = "${(64 + i).toChar()}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.background,modifier=Modifier.padding(start = 15.dp)
                                )
                            }
                        }
                        if (j != 8) Spacer(modifier = Modifier.width(if (j == 4) 25.dp else 8.dp))

                    }

                }
                Spacer(modifier = Modifier.height(if (i != 6) 8.dp else 25.dp))

            }
        }
        Row(modifier = Modifier
            .padding(vertical = 10.dp)
            .constrainAs(b) {
                top.linkTo(t.bottom)

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
                    .fillMaxWidth()
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

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = true)
fun TicketActivityPre() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        TicketActivityComp()
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
    onClick: (Boolean, String) -> Unit = { _, _ -> }
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