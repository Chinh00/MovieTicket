package com.superman.movieticket.ui.order.ticket

import android.content.Intent
import com.superman.movieticket.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

class TicketBookedDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketBookDoneComp()
        }

    }
}

@Composable
fun TicketBookDoneComp() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val t = createRef()
        val btn_bottom = createRef()
        val colorText = Color.White
        val s = createRef()
        Row(modifier = Modifier
            .constrainAs(s) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)

                end.linkTo(parent.end)

            }
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(50.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { }
                    .padding(start = 5.dp))

        }
        Column(modifier = Modifier
            .padding(10.dp).clip(RoundedCornerShape(20.dp))
            .constrainAs(t) {
                top.linkTo(s.bottom)

//                bottom.linkTo(btn_bottom.top)
            }
            .background(Color(0xE4606063))) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column {
                    Text(text = "Godzilla x Kong", color = colorText, fontSize = 20.sp)
                    Text(text = "U/A |     2D|     1h30’", color = colorText)

                }
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.moi), modifier = Modifier
                            .size(200.dp, 100.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            ), contentDescription = null, contentScale = ContentScale.FillBounds
                    )
                }
            }
            Row {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
            }

            Column(
                modifier = Modifier
                    .height(80.dp).padding(horizontal = 10.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = colorText
                    )
                    Text(text = "BHD Phạm Ngọc Thạch", color = colorText, fontSize = 20.sp)

                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = "Tầng 8, vincom Pham Ngoc Thach", color = colorText)

                }
            }
            Row {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
            }
            Column(
                modifier = Modifier
                    .height(80.dp).padding(horizontal = 10.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = null,
                        tint = colorText
                    )
                    Text(
                        text = "Wed,  15 Jun 2024 |  12:30 pm",
                        color = colorText,
                        fontSize = 20.sp
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = "Tầng 8, vincom Pham Ngoc Thach", color = colorText)

                }
            }
            Row {
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth().padding(horizontal = 10.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.seats),
                        contentDescription = null,
                        tint = colorText
                    )
                    Text(text = " EUROPA 1   |   2 Seats", color = colorText, fontSize = 20.sp)

                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = "Lux plus, C4, C5", color = colorText)

                }
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Scan QR code at the intrance of the cinema hall",
                        color = colorText,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        painter = painterResource(id = R.drawable.qrr),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = buildAnnotatedString {
                        withStyle(SpanStyle()) {
                            append("Booking id: ")

                        }
                        withStyle(SpanStyle()) {
                            append("USD019823738")

                        }
                    }, color = colorText, textAlign = TextAlign.Center)

                }
            }
        }


        Row(modifier = Modifier
            .padding(5.dp)
            .constrainAs(btn_bottom) {
                bottom.linkTo(parent.bottom)
            }) {

            Button(
                onClick = {
//                    val intent = Intent(context, OrderFoodActivity::class.java)
//                    context.startActivity(intent)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(5.dp)
                    .shadow(
                        elevation = 10.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Yellow
                    ), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFF1DC24),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(text = "ADD THE REMINDER")
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun TicketBookDonePre() {
    TicketBookDoneComp()
}
