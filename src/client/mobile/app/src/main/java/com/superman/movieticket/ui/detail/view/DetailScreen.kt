package com.superman.movieticket.ui.detail.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.home.control.HomeScreenViewModel
import com.superman.movieticket.ui.theme.MyAppTheme
import com.superman.movieticket.ui.theme.YoutubePlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.format.TextStyle

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeScreenViewModel = HomeScreenViewModel()

        val imageUri = intent.getIntExtra("imageUri", 0) // Retrieve the image URI from intent
        setContent {
            MyAppTheme {
                DetailScreen(homeScreenViewModel.listMoviesNowing.value[0])

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(m: Movie) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var isOpenBottomSheet = rememberSaveable {
        mutableStateOf(false)

    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
    ) {
        val (s, t, e, b) = createRefs()

        Box(modifier = Modifier.constrainAs(s) {
            top.linkTo(parent.top)
        }) {

            Image(
                painter = painterResource(id = R.drawable.moi),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .blur(5.dp)
                    .scale(1.3f)
                    .offset(0.dp, -30.dp),
                contentDescription = null
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(top = 60.dp, bottom = 5.dp)
            .constrainAs(e) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }) {
            ConstraintLayout {
                val (c1, c2) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.kingkong2024),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(250.dp, 330.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shadow(
                            8.dp, shape = MaterialTheme.shapes.medium, spotColor = Color(
                                0xFF1B1B83
                            ), ambientColor = Color(0xFF1B1B83)
                        )
                        .constrainAs(c1) {
                            top.linkTo(parent.top)
                        },
                    contentDescription = null
                )
                Row(modifier = Modifier
                    .constrainAs(c2) {
                        top.linkTo(c1.top)
                        bottom.linkTo(c1.bottom)
                        start.linkTo(c1.start)
                        end.linkTo(c1.end)
                    }) {
                    IconButton(
                        onClick = { isOpenBottomSheet.value =true },
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.play_icon),
                            contentDescription = null
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Turning Red",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.star_fill),
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.star_fill),
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.star_fill),
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.star_fill),
                        tint = Color.Yellow,
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(22.dp),
                        painter = painterResource(id = R.drawable.star_fill),
                        tint = Color.Gray,
                        contentDescription = null
                    )

                }
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        text = "2022  |  100 minutes  |  13+",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            val scrollDesc = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                DescriptionText(text = m.description, verticalScroll = scrollDesc)
            }
        }

        Row(modifier = Modifier
            .padding(bottom = 20.dp, end = 10.dp, start = 10.dp)
            .constrainAs(b) {
                bottom.linkTo(parent.bottom)
            }) {
            CustomButton(
                onClick = { /*TODO*/ },
                text = "Booking Tiket",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )

        }


    }
val videotrailer = rememberSaveable {
    mutableStateOf(m.trailer)
}
    if (isOpenBottomSheet.value) {
        ModalBottomSheet(

            onDismissRequest = {isOpenBottomSheet.value=false}, sheetState = sheetState, modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
        , shape = MaterialTheme.shapes.medium) {
            YoutubePlayer(videotrailer.value, LocalLifecycleOwner.current)
        }
    }
}

@Composable
fun DescriptionText(text: String, verticalScroll: ScrollState) {
    val seeMore = remember { mutableStateOf(true) }
    Column(
        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(end = 15.dp, start = 15.dp)
            .verticalScroll(verticalScroll)
    ) {
        val gradientColors = listOf(Color.White, Color.Gray, Color.DarkGray)
        Text(
            text = text + text + text + text,
            style = androidx.compose.ui.text.TextStyle(
                brush = Brush.verticalGradient(gradientColors),
                textAlign = TextAlign.Justify
            ),
            maxLines = if (seeMore.value) 2 else Int.MAX_VALUE,
            fontSize = 20.sp,
            overflow = if (seeMore.value) TextOverflow.Ellipsis else TextOverflow.Clip,
            color = Color.White, modifier = Modifier.clickable { seeMore.value = !seeMore.value }
        )
        Text(
            modifier = Modifier.clickable { seeMore.value = !seeMore.value },
            text = if (seeMore.value) "Xem thêm" else "Thu gọn",
            style = androidx.compose.ui.text.TextStyle(
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1459D1)
            )
        )
    }
}

@Composable
@Preview(showSystemUi = true)

fun DetailScreenPre() {
    val homeScreenViewModel = HomeScreenViewModel()

    DetailScreen(homeScreenViewModel.listMoviesNowing.value[0])
}

@SuppressLint("RememberReturnType")
@Composable
fun ListScreen(context1: Context) {
    val homeScreenViewModel = HomeScreenViewModel()
    var data = remember { mutableStateOf<List<Movie>>(emptyList()) }

//    LaunchedEffect(key1 = data) {
//        data = homeScreenViewModel.listMoviesNowing as MutableState<List<Movie>>
//    }

    Column(modifier = Modifier.wrapContentSize()) {
        if (!data.value.isEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                itemsIndexed(data.value) { index, movie ->
                    Text(text = "Item $index")
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
        val scope = rememberCoroutineScope()

        Button(onClick = {
            scope.launch {
                delay(3000L)
                data.value = homeScreenViewModel.listMoviesNowing.value

            }
        }, modifier = Modifier.size(100.dp)) {
            Text(text = "Get Data")
        }
    }


}


@Composable
fun ListScreenPre() {
    val context = LocalContext.current
    ListScreen(
        context
    )
}
