package com.superman.movieticket.ui.order.ticket

import android.content.Intent
import com.superman.movieticket.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    ConstraintLayout(modifier= Modifier
        .fillMaxSize()
        .background(Color.Black)) {
val t = createRef()
        val btn_bottom = createRef()

        Row(modifier = Modifier
            .padding(5.dp)
            .constrainAs(t) {
                top.linkTo(parent.top)

                bottom.linkTo(btn_bottom.top)
            }
            .background(Color.Gray)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(text = "Godzilla x Kong")
                    Text(text = "U/A |         2D|            1h30’")

                }
                Column {
                    Image(painter = painterResource(id = R.drawable.quaivat), contentDescription = null, contentScale = ContentScale.FillBounds)
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
                Icon(imageVector = Icons.Outlined.Notifications, contentDescription = null, tint = Color.Black)
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
