package com.superman.movieticket.ui.order.food


import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.Service
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.order.food.control.OrderFoodActivityModel
import com.superman.movieticket.ui.order.model.ReservationCreateModel
import com.superman.movieticket.ui.order.model.ServiceReservationsCreateModel
import com.superman.movieticket.ui.order.payment.hooks.NavigatePaymentTicket
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseScreen(content = {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    OrderFoodScreen(Gson().fromJson(intent.getStringExtra("ReservationCreateModel"),ReservationCreateModel::class.java))
                }
            }, title = "Chọn đồ")
        }
    }
}
enum class ChangeServiceAction {
    ADD,
    REMOVE
}

@Composable
fun OrderFoodScreen(reservationCreateModel: ReservationCreateModel) {
    val orderFoodActivityModel: OrderFoodActivityModel = hiltViewModel()
    val context = LocalContext.current
    var totalPrice = remember {
        mutableStateOf(0)
    }


    val reservationCreateModelState = remember {
        mutableStateOf(reservationCreateModel)
    }
    fun ChangeService (serviceId: String, changeServiceAction: ChangeServiceAction = ChangeServiceAction.ADD){
        when(changeServiceAction) {
            ChangeServiceAction.ADD -> {
                val serviceDetail = reservationCreateModelState.value.serviceReservations.firstOrNull { it.serviceId == serviceId }
                if (serviceDetail != null) {
                    serviceDetail.quantity += 1
                } else {
                    reservationCreateModelState.value.serviceReservations.add(
                        ServiceReservationsCreateModel(serviceId, 1)
                    )
                }
            }
            ChangeServiceAction.REMOVE -> {
                val serviceDetail = reservationCreateModelState.value.serviceReservations.firstOrNull { it.serviceId == serviceId }
                if (serviceDetail != null) {
                    serviceDetail.quantity -= 1
                }
            }
        }
    }




    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF2B2E50))
    ) {
        val (s, e, b, t) = createRefs()
        Column(
            Modifier
                .constrainAs(t) {
                    top.linkTo(e.bottom)
                    bottom.linkTo(b.top)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.dp)) {

            val listFood = orderFoodActivityModel.listService.collectAsState().value

            listFood.forEach {
                ItemFood(service = it, totalPrice = totalPrice.value) {
                    totalPrice.value = it
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(b) {
                    bottom.linkTo(parent.bottom)
                }
                .padding(bottom = 10.dp)

        ) {
            Button(
                onClick = {
                    NavigatePaymentTicket(
                        context,
                        reservationCreateModelState.value
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF1DC24),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Yellow
                    )
            ) {
                Text(text = " Thanh toán ${totalPrice.value}")
            }
        }
    }
}

@Composable
fun ItemFood(
    service: Service,
    totalPrice: Int,
    setTotalPrice: (Int) -> Unit
) {
    val c = Color(0xFFA8F54E)


    val quantity = rememberSaveable {
        mutableStateOf(0)
    }


    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .height(70.dp)
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = AppOptions.BASE_URL + "/admin-api/image/" + service.avatar,
                error = painterResource(
                    id = R.drawable.error_img
                )
            ),
            contentDescription = "", contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(50.dp, 50.dp)
        )

        Column {
            Row {
                Text(text = service.name + "(${service.unit})")
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    IconButton(onClick = {
                        if (quantity.value > 0) {
                            quantity.value -= 1
                            setTotalPrice(totalPrice - service.priceUnit)
                        }


                    }) {
                        Text(text = "-", fontSize = 30.sp, color = c)
                    }
                }
                Column {
                    Text(text = "${quantity.value}", fontSize = 25.sp)
                }
                Column {
                    IconButton(onClick = {
                        quantity.value += 1
                        setTotalPrice(totalPrice + service.priceUnit)
                    }) {
                        Text(text = "+", fontSize = 30.sp, color = c)
                    }
                }
            }
        }
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = "${service.priceUnit} đ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}









