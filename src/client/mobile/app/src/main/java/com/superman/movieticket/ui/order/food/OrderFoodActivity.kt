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
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.order.food.control.MovieFood
import com.superman.movieticket.ui.order.food.control.OrderFoodActivityModel
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
                    OrderFoodScreen()
                }
            }, title = "Chọn đồ")
        }
    }
}

@Composable
fun OrderFoodScreen() {
    val orderFoodActivityModel: OrderFoodActivityModel = hiltViewModel()
    val totalAmount = orderFoodActivityModel.totalPriceFood.collectAsState()
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF2B2E50))
    ) {
        val (s, e, b, t) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(e) {
                    top.linkTo(parent.top)
                }
                .background(Color(0xFFD3D3D3))
                .height(150.dp),
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Doremon the\n" +
                                "Movies: Nobita\n" +
                                "earth symphony",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "P", color = Color.White, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                            .background(Color(0xFFD58611))
                            .padding(15.dp, 5.dp)

                    )
                    Text(
                        text = "2 Seat", color = Color.White, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(5.dp)
                            )
                            .background(Color(0xFFD58611))
                            .padding(15.dp, 5.dp)

                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1.5f)
            ) {
                Row {
                    Text(
                        text = "Subtotal ( including surcharges )",
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "${totalAmount.value}đ",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }

        }

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
            val selectFoodPrice = remember { mutableStateOf(0f) }
            val listFood = orderFoodActivityModel.loadingListFood()
            val c = LocalContext.current
            listFood.forEach {
                ItemFood(it,
                    onClickedMinimusQuantity = { quantity, it ->
                        if (quantity > 0) {
                            orderFoodActivityModel.setTotalPrice(totalAmount.value - (it.price.toInt()))

                        }


                        Toast.makeText(
                            c,
                            "Ban tru mot ${totalAmount.value} - ${(it.price.toInt())}",
                            Toast.LENGTH_SHORT
                        ).show()

//                        Log.e("Quantity trừ","quantity minus ${quantity}")
//                        Log.e("Total trừ","quantity minus ${quantity.toInt()*it.price.toInt()}")
                    },
                    onClickedAddQuantity = { quantity, it ->
                        orderFoodActivityModel.setTotalPrice(totalAmount.value + (it.price.toInt()))
                        Toast.makeText(
                            c,
                            "Ban cong  ${totalAmount.value} + ${(it.price.toInt())}",
                            Toast.LENGTH_SHORT
                        ).show()


                    })

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

//                    val intent = Intent(context, PaymentActivity::class.java)
//                    context.startActivity(intent)
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
                Text(text = " Finish Payment")
            }
        }
    }
}

@Composable
fun ItemFood(
    f: MovieFood,
    onClickedMinimusQuantity: (Int, MovieFood) -> Unit,
    onClickedAddQuantity: (Int, MovieFood) -> Unit
) {
    val c = Color(0xFFA8F54E)
    var quantity = rememberSaveable {
        mutableStateOf(0)
    }
    var totalPrice = remember {
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
        Image(painter = painterResource(id = f.img), contentDescription = null)

        Column {
            Row {
                Text(text = f.name)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    IconButton(onClick = {
                        if (quantity.value > 0) {
                            onClickedMinimusQuantity(quantity.value--, f)
                        } else {
                            onClickedMinimusQuantity(0, f)
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
                        quantity.value++
                        onClickedAddQuantity(1, f)
                    }) {
                        Text(text = "+", fontSize = 30.sp, color = c)
                    }
                }
            }
        }
        Column {

            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = "${f.price}00 đ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
//@Preview(showSystemUi = true)
fun ItemFoodnPre() {
    val viewModel: OrderFoodActivityModel = hiltViewModel()
    ItemFood(
        f = viewModel.loadingListFood().get(0),
        onClickedMinimusQuantity = { quantity, f ->

        }, onClickedAddQuantity = { quantity, f ->

        }
    )
}

@Composable
@Preview(showSystemUi = true)
fun OrderFoodScreenPre() {
    OrderFoodScreen()
}






