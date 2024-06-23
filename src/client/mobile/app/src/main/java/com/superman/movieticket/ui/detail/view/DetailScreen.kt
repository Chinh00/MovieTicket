package com.superman.movieticket.ui.detail.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.Category
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.auth.hooks.NavigateLogin
import com.superman.movieticket.ui.components.BaseScreen
import com.superman.movieticket.ui.components.CustomButton

import com.superman.movieticket.ui.components.VideoViewer
import com.superman.movieticket.ui.detail.control.DetailActivityViewModel
import com.superman.movieticket.ui.home.control.HomeScreenViewModel
import com.superman.movieticket.ui.order.screening.hooks.NavigateScreenActivity
import com.superman.movieticket.ui.theme.CustomColor4
import com.superman.movieticket.ui.theme.MyAppTheme
import com.superman.movieticket.ui.theme.YoutubePlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.TextStyle

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val detailActivityViewModel: DetailActivityViewModel = hiltViewModel()
            val movie by detailActivityViewModel.movie.observeAsState()
            val id = intent.getStringExtra("id")
            LaunchedEffect(Unit) {
                detailActivityViewModel.getMovieById(id.toString())

            }
            Log.d("Chinh", id.toString())

            MyAppTheme {
                BaseScreen(content = {
                    movie?.let {
                        DetailScreen(it)

                    }
                }, title = "", onNavigateUp = { finish() })


            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(m: Movie) {
    val context = LocalContext.current
    val loginSocialViewModel: LoginActivityViewModel = hiltViewModel()
    val login by loginSocialViewModel.isLogin.collectAsState(initial = null)


    var isFullscreen by rememberSaveable { mutableStateOf(false) }
    fun HandleTicket(id: String) {
        NavigateScreenActivity(
            context = context,
            movieId = id
        )
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (s, t, e, b) = createRefs()
        val context = LocalContext.current
        Box(
            modifier = Modifier.constrainAs(s) {
                top.linkTo(parent.top)
            }
        ) {
            VideoViewer(
                url = AppOptions.BASE_URL + m.trailer,
                image = AppOptions.BASE_URL + m.avatar,
                onFullscreenToggle = { isFullscreen = it }
            )

//            YoutubePlayer(
//                youtubeId = AppOptions.BASE_URL + m.trailer,
//                lifecycleOwner = LocalLifecycleOwner.current
//            )
        }

        Row(modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxHeight()
            .constrainAs(e) {
                top.linkTo(s.bottom)

            }) {
            DetailItemScreen(m = m, rememberScrollState())
        }


        AnimatedVisibility(!isFullscreen, modifier = Modifier
            .padding(bottom = 20.dp, end = 10.dp, start = 10.dp)
            .constrainAs(b) {
                bottom.linkTo(parent.bottom)
            }) {
            Row() {
                CustomButton(
                    onClick = {
                        if (login != "true") {
                            NavigateLogin(context)
                        } else {
                            HandleTicket(m.id)
                        }
                    },
                    text = context.getString(R.string.txt_book_ticket),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), MaterialTheme.colorScheme.onPrimaryContainer
                )

            }
        }


    }


}

@Composable
fun DescriptionText(text: String, verticalScroll: ScrollState) {
    val seeMore = remember { mutableStateOf(true) }
    Column(
        Modifier
            .verticalScroll(verticalScroll)
            .fillMaxSize()
            .fillMaxHeight()
            .padding(end = 15.dp, start = 15.dp, bottom = 50.dp)
    ) {
        val gradientColors = listOf(
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.secondaryContainer
        )
        Text(
            text = text,
            style = androidx.compose.ui.text.TextStyle(
                brush = Brush.verticalGradient(gradientColors),
                textAlign = TextAlign.Justify, fontSize = 16.sp, fontWeight = FontWeight.Bold
            ),
            maxLines = if (seeMore.value) 2 else Int.MAX_VALUE,
            fontFamily = FontFamily.Default,
            overflow = if (seeMore.value) TextOverflow.Ellipsis else TextOverflow.Clip,
            color = Color.White, modifier = Modifier
                .clickable { seeMore.value = !seeMore.value }
        )
//        Text(
//            modifier = Modifier.clickable { seeMore.value = !seeMore.value },
//            text = if (seeMore.value) "Xem thêm" else "Thu gọn",
//            style = androidx.compose.ui.text.TextStyle(
//                textAlign = TextAlign.Justify,
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                color = Color(0xFF1459D1)
//            )
//        )
    }
}

//@Composable
//@Preview(showSystemUi = true)
//
//fun DetailScreenPre() {
//    val detailActivityViewModel = DetailActivityViewModel()
//
//val movie by detailActivityViewModel.movie.observeAsState()
//    MyAppTheme {
//        detailActivityViewModel.setMovie(1)
//        movie?.let { DetailScreen(it) }
//    }
//}

//@SuppressLint("RememberReturnType")
//@Composable
//fun ListScreen(context1: Context) {
//    val homeScreenViewModel = HomeScreenViewModel()
//    var data = remember { mutableStateOf<List<Movie>>(emptyList()) }
//
////    LaunchedEffect(key1 = data) {
////        data = homeScreenViewModel.listMoviesNowing as MutableState<List<Movie>>
////    }
//
//    Column(modifier = Modifier.wrapContentSize()) {
//        if (!data.value.isEmpty()) {
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(20.dp)) {
//                itemsIndexed(data.value) { index, movie ->
//                    Text(text = "Item $index")
//                }
//            }
//        } else {
//            CircularProgressIndicator(modifier = Modifier.size(50.dp))
//        }
//        val scope = rememberCoroutineScope()
//
//        Button(onClick = {
//            scope.launch {
//                delay(3000L)
//                data.value = homeScreenViewModel.listMoviesNowing.value
//
//            }
//        }, modifier = Modifier.size(100.dp)) {
//            Text(text = "Get Data")
//        }
//    }
//
//
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailItemScreen(m: Movie, scroll: ScrollState) {
    val context = LocalContext.current
    val colorText = MaterialTheme.colorScheme.onBackground
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(vertical = 5.dp)) {
            Text(
                text = "${m.name ?: "Ddang caap nhat"}".uppercase(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = colorText
            )
        }
        Row() {
            Column {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = AppOptions.BASE_URL + m.avatar,
                        error = painterResource(id = R.drawable.moi)
                    ),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(120.dp, 180.dp), contentScale = ContentScale.FillHeight,
                    contentDescription = m.name
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Row {
                    Text(
                        text = "${context.getString(R.string.txt_duration)}: ",
                        modifier = Modifier.width(80.dp),
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = " ${m.totalTime} ${context.getString(R.string.txt_minutes)}",
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Row {
                    Text(
                        text = "${context.getString(R.string.txt_release_date)}: ",
                        modifier = Modifier.width(80.dp),
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                            m.releaseDate,
                            "dd/MM/yyyy"
                        ),
//                        text = "__",
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Row {
                    Text(
                        text = "${context.getString(R.string.txt_category)}: ",
                        modifier = Modifier.width(80.dp),
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "${
                            if (m.categories.size > 0) {
                                m.categories.joinToString(separator = " | ") { category -> category.name }
                            } else {
                                "Đang cập nhật"
                            }
                        } ",
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Row {
                    Text(
                        text = "Loại: ", modifier = Modifier.width(80.dp),
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "2D | Normal| Normal sound",
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall
                    )

                }
                Row {
                    Text(
                        text = "${context.getString(R.string.txt_rating)}:",
                        color = colorText,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.width(80.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(22.dp),
                            painter = painterResource(id = R.drawable.star_fill),
                            tint = Color.Yellow,
                            contentDescription = null
                        )
                        Text(
                            text = "9.6",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxHeight()
        ) {
            DescriptionText(text = m.description, verticalScroll = scroll)
        }

    }
}

