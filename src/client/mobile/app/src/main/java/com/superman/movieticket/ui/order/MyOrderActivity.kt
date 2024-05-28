package com.superman.movieticket.ui.order

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.home.model.listMovies
import com.superman.movieticket.ui.profile.Image

class MyOrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyOrderComp()
        }
    }
}

@Composable
fun MyOrderComp() {
    val scrollVertical = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        val (t, b) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(t) {
                    top.linkTo(parent.top)
                },
            verticalAlignment = Alignment.CenterVertically

        ) {
            androidx.compose.material.Icon(
                Icons.Outlined.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(36.dp)
            )
            androidx.compose.material.Text(
                text = "My Order",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                fontSize = 20.sp,
                color = Color.White, fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier
            .constrainAs(b) {
                top.linkTo(t.bottom)
            }
            .verticalScroll(scrollVertical), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            (1..10).forEach {
                MyOrderItemComp(listMovies[1]) {

                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MyOrderPre() {
    MyOrderComp()
}

@Composable
fun MyOrderItemComp(m: com.superman.movieticket.ui.home.model.Movie, onclickItem: (Any?) -> Unit) {
    val colorText = Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(105.dp)
            .clickable { onclickItem(m) }
    ) {

        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.exhuma),
            contentDescription = null, modifier = Modifier.clip(RoundedCornerShape(10.dp))
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(105.dp)
        ) {
            Text(
                text = "Quật Mộ Trùng Ma",
                color = Color.Red, fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = null, modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = "BHD Phạm Ngọc Thạch",
                        color = colorText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.schedule),
                        contentDescription = null, modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)


                    )
                    Text(
                        text = "24 tháng 03, 2024",
                        color = colorText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.timemanagement),
                        contentDescription = null, modifier = Modifier
                            .padding(end = 5.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = "18:00 ~ 20:06",
                        color = colorText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

        }

    }

}

@Composable
@Preview(showSystemUi = true)
fun MyOrderItemPre() {
//    MyOrderItemComp()
}

val balooTamma = FontFamily(
    Font(resId = R.font.balootamma2_bold, FontWeight.Bold),

    Font(resId = R.font.balootamma2_medium, FontWeight.Medium),
    Font(resId = R.font.balootamma2_regular, FontWeight.Normal),
    Font(resId = R.font.balootamma2_semibold, FontWeight.SemiBold),
    Font(resId = R.font.balootamma2_extrabold, FontWeight.ExtraBold),

    )