package com.superman.movieticket.ui.order.food

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superman.movieticket.core.entity.BaseEntity
import com.superman.movieticket.domain.entities.EntityBase
import java.util.Stack

class OrderFoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.show()

        setContent {

            Surface(
                modifier = Modifier.fillMaxSize()
                    .safeContentPadding()
            ) {
                val viewModel: OrderFoodActivityModel =
                    ViewModelProvider(this).get(OrderFoodActivityModel::class.java)
                OrderFoodScreen(viewModel)
            }
        }
    }
}

@Composable
fun OrderFoodScreen(viewModel: OrderFoodActivityModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var totalAmount = viewModel.totalPriceFood.collectAsState()



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()

            .background(Color(0xFF2B2E50))
    ) {
        val (s, e, b, t) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(s) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(t.top)

                }
                .fillMaxWidth()
                .background(Color(0xFF2B2E50))
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .weight(0.3f)
                    .size(36.dp).clickable {  }
                    .padding(start = 5.dp)
            )
            androidx.compose.material.Text(
                text = "Session Selection",
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(vertical = 5.dp, horizontal = 3.dp)
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    ) {
                        append("Pay in: ")
                    }
                    withStyle(SpanStyle(color = Color(0xFFC57E14))) {
                        append("6:30")
                    }
                }, color = Color.Black)
            }
            Spacer(modifier = Modifier.width(5.dp))

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(e) {
                    top.linkTo(t.bottom)
                    bottom.linkTo(b.top)
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
                Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
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
                    top.linkTo(s.bottom)
                    bottom.linkTo(e.top)
                }
                .fillMaxWidth()
                .padding(horizontal = 15.dp)) {
            Row {
                Text(text = "BHD Phạm Ngọc Thạch", color = Color.White, fontSize = 20.sp)
            }
            Row {
                Text(
                    text = "Screen 5 -24 tháng 01, 2024   12:30 ",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            ) {
                Text(text = "Conecssion", color = Color.White, fontSize = 16.sp)

            }
            val selectFoodPrice = remember { mutableStateOf(0f) }
            val listFood = viewModel.loadingListFood()
            val c = LocalContext.current
            listFood.forEach {
                ItemFood(it,
                    onClickedMinimusQuantity = { quantity, it ->
                        if(quantity>0){
                            viewModel.setTotalPrice(totalAmount.value - (it.price.toInt()))

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
                        viewModel.setTotalPrice(totalAmount.value + (it.price.toInt()))
                        Toast.makeText(
                            c,
                            "Ban cong  ${totalAmount.value} + ${(it.price.toInt())}",
                            Toast.LENGTH_SHORT
                        ).show()


//                        totalAmount.value += it.price.toInt() * quantity
                    })

            }

        }
        Row(
            modifier = Modifier
                .constrainAs(b) {
                    bottom.linkTo(parent.bottom)
                    top.linkTo(e.bottom)
                }
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD58611),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
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
    var quantity = rememberSaveable{
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
                        if(quantity.value>0){
                            onClickedMinimusQuantity(quantity.value--, f)
                        }else{
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
    val viewModel: OrderFoodActivityModel = viewModel()
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






