package com.superman.movieticket.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize

import com.superman.movieticket.R
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.ui.home.model.listMovies
import com.superman.movieticket.ui.order.ticket.TicketActivity

class DetailsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            DetailScreen()
        }
    }


}

@Composable
private fun DetailScreen() {
    var context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .background(color = Color.Black)
    ) {
        val (t, b, ic1, ic2, ic3, bgbottom) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.kingkong2024), modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)

                .constrainAs(t) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, contentScale = ContentScale.Fit, contentDescription = ""
        )
        Box(
            modifier = Modifier
                .background(brush = Brush.verticalGradient(listOf(Color(0x0C958A8A), Color.Black)))
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(b) {
                    top.linkTo(t.bottom)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Column {
                Row {
                    Text(
                        text = "Godzilla x Kong",
                        maxLines = 2, fontSize = 30.sp, color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row {
                    Text(
                        text = "Đế CHẾ Kong".uppercase(),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        maxLines = 2,
                        fontSize = 40.sp
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    val c = Color.Yellow
                    val radius = RoundedCornerShape(8.dp)
                    Column(Modifier.wrapContentSize()) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(5.dp)
                        ) {
                            Icon(
                                tint = c,
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = R.drawable.star_fill),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = " 8.5", color = c)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .clip(radius)
                                .background(Color.Gray)
                                .padding(5.dp)
                        ) {
                            Text(text = "Action")
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(Modifier.wrapContentSize()) {

                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .clip(radius)
                                .background(c)
                                .padding(5.dp)
                        ) {
                            Text(text = "IMDB 7.5")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .width(100.dp)
                                .clip(radius)
                                .background(Color.Gray)
                                .padding(5.dp)
                        ) {
                            Text(text = "1h 72m")
                        }
                    }

                }
                val state = rememberScrollState()

                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                        .verticalScroll(state)
                ) {
                    LaunchedEffect(Unit) { state.animateScrollTo(100) }
                    repeat(10) {
                        val gradientColors = listOf(Color.White, Color.Gray, Color.DarkGray)
                        Text(
                            text = listMovies[0].description + listMovies[0].description + listMovies[0].description,
                            style = TextStyle(
                                brush = Brush.verticalGradient(gradientColors),
                                textAlign = TextAlign.Justify
                            ),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }

            }
        }

        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier
                .size(90.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .constrainAs(ic1) {
                    start.linkTo(parent.start)
                }, colors = IconButtonDefaults.iconButtonColors(

                containerColor = Color(0xC49B592C), contentColor = Color.White
            )

        ) {
            Icon(Icons.Outlined.ArrowBack, contentDescription = "")
        }
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier
                .size(90.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .constrainAs(ic2) {
                    end.linkTo(parent.end)
                }, colors = IconButtonDefaults.iconButtonColors(

                containerColor = Color(0xEDA55C29), contentColor = Color.White
            )

        ) {
            Icon(
                painter = painterResource(id = R.drawable.bookmark),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
        }
        IconButton(
            onClick = { /*TODO*/ }, modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Transparent)
                .constrainAs(ic3) {
                    top.linkTo(parent.top)
                    bottom.linkTo(b.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, colors = IconButtonDefaults.iconButtonColors(

                contentColor = Color.White
            )

        ) {
            Image(
                painter = painterResource(id = R.drawable.play_icon),
                modifier = Modifier.size(100.dp),
                contentDescription = ""
            )
        }

        Button(
            onClick = {
                val intent = Intent(context, TicketActivity::class.java)
                context.startActivity(intent)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(bottom = 5.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color = Color(0xFFDB4E22))
                .constrainAs(bgbottom) {

                    bottom.linkTo(parent.bottom)
                }, colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                painter = painterResource(id = R.drawable.ticket),
                contentDescription = null,
                tint = Color.White
            )
            Text(text = "   Book", fontSize = 20.sp)
        }


    }
}

@Composable
@Preview(showBackground = true)
private fun DetailScreenPre() {
    DetailScreen()
}

































































