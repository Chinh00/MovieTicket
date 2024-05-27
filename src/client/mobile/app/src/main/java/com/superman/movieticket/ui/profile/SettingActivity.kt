package com.superman.movieticket.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.superman.movieticket.R

import com.superman.movieticket.ui.home.model.Movie
import com.superman.movieticket.ui.home.model.listMovies
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.annotation.meta.When

class SettingActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingActivityComp()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SettingActivityComp() {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = "Profile",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            LazyColumn {
                item {
                    Image(context, listMovies.first())
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            // Thêm phần thanh ngang
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_language_24),
                    contentDescription = "Language",
                    tint = Color.White,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(top = 16.dp),
                )
                Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa Icon và Text
                Text(
                    text = "Language",
                    color = Color.White,
                    fontSize = 25.sp,

                )


            }

            val tabItems = listOf("Việt Nam", "English")
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            Column(modifier = Modifier.wrapContentSize()) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color(0xE6C7C7C7), indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                                .height(0.dp)
                                .width(0.dp)
                        )
                    },
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(30.dp)
                        )
                        .background(color = Color(0xE6C7C7C7))
                ) {
                    tabItems.forEachIndexed { index, title ->
                        val c = remember {
                            Animatable(Color(0xE6C7C7C7))
                        }
                        LaunchedEffect(key1 = pagerState.currentPage == index) {
                            c.animateTo(
                                if (pagerState.currentPage == index) Color(0xFFDE7101) else Color(
                                    0xE6C7C7C7
                                )
                            )
                        }

                        androidx.compose.material3.Tab(
                            text = {
                                androidx.compose.material3.Text(
                                    text = title,
                                    style = if (pagerState.currentPage == index) androidx.compose.ui.text.TextStyle(
                                        color = Color.White,
                                        fontSize = 18.sp
                                    ) else androidx.compose.ui.text.TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                )
                            },
                            selected = pagerState.currentPage == index, modifier = Modifier
                                .clip(
                                    RoundedCornerShape(30.dp)
                                )
                                .background(color = c.value), onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            })
                    }
                }
                Spacer(modifier = Modifier.height(13.dp));
                HorizontalPager(
                    state = pagerState, count = 1, modifier = Modifier
                        .fillMaxSize()
                )
                {

                }


            }

        }
    }

}

fun Text(text: String, style: TextStyle) {

}

@Composable
fun Image(context: Context, movie: Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val image = createRef()

            Image(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clip(CircleShape)
                    .size(100.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null
            )
        }

    }
    Text(
        text = "Dong Chinh Khanh",
        color = Color.White,
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
            .padding(top = 20.dp)
    )
    Text(
        text = "@123promax",
        color = Color.White,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
    )

}
@Composable
@Preview(showSystemUi = true)
fun SettingActivityPre() {
    SettingActivityComp()
}