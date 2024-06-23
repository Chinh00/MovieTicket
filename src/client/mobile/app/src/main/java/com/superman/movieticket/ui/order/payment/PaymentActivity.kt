package com.superman.movieticket.ui.order.payment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceControl.Transaction
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Measured
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.gson.Gson
import com.superman.movieticket.R
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.main.MainActivity
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.payment.control.PaymentActivityViewModel
import com.superman.movieticket.ui.theme.balooFont
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = { PaymentComp(Gson().fromJson(intent.getStringExtra("ReservationCreateModel"),ReservationCreateModel::class.java)) }, title = "",
                onNavigateUp = {
                    finish()
                })
        }
    }

}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PaymentComp(
    reservationCreateModel: ReservationCreateModel
) {
    val paymentActivityViewModel: PaymentActivityViewModel = hiltViewModel()
    val context = LocalContext.current
    val loading = paymentActivityViewModel.apiLoading.collectAsState()

    LaunchedEffect(key1 = Unit) {
        paymentActivityViewModel.HandleCreateTransactionAsync(60000)
        paymentActivityViewModel.hubConnection.start().blockingAwait()
        paymentActivityViewModel.hubConnection.on("ConfirmPayment", { transaction: Transaction ->
            Log.d("Chinh", transaction.toString())
        }, Transaction::class.java)
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val context = LocalContext.current
        val doneActMethod = remember {
            mutableStateOf(false)
        }
        val doneActDieuKhoan = remember {
            mutableStateOf(false)
        }
        val (s, e, t, b) = createRefs()
        Column(modifier = Modifier.constrainAs(s) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
            PaymentTopComp("King Kong GodziLa", context, R.drawable.trangquynh) {}
        }
        Column(modifier = Modifier
            .wrapContentSize()
            .constrainAs(e) {
                top.linkTo(s.bottom)
            }) {
            PaymentContentComp("Items Ordered") {
                PaymentTotaltComp("2 x FC - Week - holiday - 2D - park : C4, C5", 180.00)
            }
        }
        Column(modifier = Modifier
            .padding(top = 30.dp)
            .constrainAs(t) {
                top.linkTo(e.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(b.top)
            }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_3),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(300.dp)
            )
        }
        Column(modifier = Modifier.constrainAs(b) {
            top.linkTo(t.bottom)
            bottom.linkTo(parent.bottom)

        }) {
            PaymentFooterComp(false,
                onClicked = {

                }, onClickMethod = {

                })
        }


    }


}


@Composable
fun PaymentTopComp(title: String, context: Context, img: Any?, onBackClick: (Context) -> Unit) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (s, e, t, b) = createRefs()

        Image(
            painter = painterResource(id = img.toString().toInt()),
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .constrainAs(s) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                },
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )



        Text(text = title,
            fontWeight = FontWeight.Bold,
            fontFamily = balooFont,
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier.constrainAs(e) {
                top.linkTo(s.top)
                start.linkTo(s.start)
                end.linkTo(s.end)
                bottom.linkTo(s.bottom)
            })
        Row(modifier = Modifier
            .padding(bottom = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(vertical = 5.dp, horizontal = 3.dp)
            .constrainAs(b) {
                end.linkTo(parent.end)
                bottom.linkTo(s.bottom)
            }) {
            androidx.compose.material3.Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 16.sp
                    )
                ) {
                    append("Pay in: ")
                }
                withStyle(SpanStyle(color = Color(0xFFC57E14))) {
                    append("6:30")
                }
            }, color = Color.Black)
        }


    }


}


@Composable
fun PaymentContentComp(title: String, content: @Composable () -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                Color.Gray
            )
    ) {}
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Row {
            Text(
                text = title,
                modifier = Modifier.padding(vertical = 10.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        content()

    }

}

@Composable
fun PaymentTotaltComp(list: Any, price: Double) {
    val color = Color.White
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            Text(
                text = list.toString(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                color = color
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "$price đ",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                color = color
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    Color.Gray
                )
        ) {}
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(
                text = "Subtotal ( including surcharge )",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                color = color
            )
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "$price đ",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Light,
                color = color
            )
        }

    }

}


@Composable
fun PaymentSelectMethodPaymenttComp(
    list: List<MethodPayment>, onClickMethod: (MethodPayment) -> Unit
) {

    var selectedMethod by remember { mutableStateOf<MethodPayment?>(null) }
    list.forEach {
        val isSelected = it == selectedMethod
        val bgColor = if (isSelected) Color(0xFFFF7E7E) else Color.Gray
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selectedMethod = it
                    onClickMethod(it)
                }, verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Image(
                        painter = painterResource(id = it.img),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(50.dp, 40.dp)
                            .clip(

                                RoundedCornerShape(10.dp)
                            ),
                        contentDescription = ""
                    )
                }
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "${it.name}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Light,
                        color = Color.White

                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(
                        bgColor
                    )
            ) {}


        }
        Spacer(modifier = Modifier.height(10.dp))

    }

}


@Composable
fun PaymentFooterComp(
    isChecked: Boolean = false,
    onClickMethod: (Boolean) -> Unit, onClicked: (Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(isChecked) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checkedState.value, onCheckedChange = {
                checkedState.value = it
                onClickMethod(it)

            })
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White)) {
                        append("Tôi đã đọc, hiểu và đồng ý với các ")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color(0xFF3EC743),
                            textDecoration = TextDecoration.Underline,
                            fontFamily = balooFont,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("điều khoản")
                    }
                },
                modifier = Modifier
                    .clickable {
                        checkedState.value = !checkedState.value

                        onClickMethod(checkedState.value)
                    }
                    .padding(start = 10.dp),
                color = Color.Black
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { onClicked(checkedState.value) }, modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Yellow
                    ), colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(text = "Finish Payment")
            }
        }
    }


}


data class MethodPayment(
    @DrawableRes val img: Int,
    val name: String,

    )

val listMethodPayment = listOf(
    MethodPayment(R.drawable.pay, "Ví ShopeePay"),
    MethodPayment(R.drawable.momo, "Ví Momo"),
    MethodPayment(R.drawable.atm, "ATM  ( Internet Banking )"),
    MethodPayment(R.drawable.visa, "ATM  ( Visa, Mater, Amex....")
)