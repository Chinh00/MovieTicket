package com.superman.movieticket.ui.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll


import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.home.control.HomeScreenViewModel
import kotlin.math.absoluteValue
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.infrastructure.utils.ApiState
import com.superman.movieticket.ui.components.ScreenLoading
import com.superman.movieticket.ui.detail.view.DetailActivity

import com.superman.movieticket.ui.theme.MyAppTheme
import com.superman.movieticket.ui.theme.balooFont
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val _imgBg = MutableStateFlow("")
val imgBg: StateFlow<String> get() = _imgBg

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen() {


    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    ScreenLoading(isLoading = homeScreenViewModel.apiState.collectAsState()) {
        MyAppTheme {
            Surface(
                modifier = Modifier.paint(
                    rememberAsyncImagePainter(model = imgBg, error = painterResource(id = R.drawable.error_img)),
                    contentScale = ContentScale.FillBounds
                )
            ) {
                HomeContent()
            }

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent() {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        HomePage()
    }
}

@Composable
fun ComingPage() {

    val homeScreenModel: HomeScreenViewModel = hiltViewModel()
    val movies = homeScreenModel.listMovies.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movies.forEach {
            ComingPageItem(m = it)
        }
    }
}


@Composable
fun ComingPageItem(m: Movie) {

    Card(
        onClick = {},
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            Column() {

                Image(
                    painter = painterResource(id = R.drawable.poster_payoff_aquaman_6_1_),
                    modifier = Modifier.clip(
                        RoundedCornerShape(8.dp)
                    ),
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .wrapContentSize(), verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row {
                    Text(
                        m.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.star_fill),
                            contentDescription = null,
                            tint = Color.Yellow
                        )
                        Text(
                            "7.2",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 5.dp),
                            color = Color.Yellow
                        )
                    }
                    Column(
                        modifier = Modifier

                            .padding(start = 10.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(MaterialTheme.colorScheme.onErrorContainer)
                    ) {
                        Text(
                            "IMDB: 9.3",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier

                                .padding(5.dp),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }


                }
                Row() {

                    Text(
                        "Chinh Văn Nguyễn",
                        style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
                        modifier = Modifier

                            .padding(5.dp),
                        color = MaterialTheme.colorScheme.surface
                    )
                }

            }


        }

    }
}

@Composable
//@Preview(showSystemUi = true)
fun ComingPagePre() {
    val homeViewModel: HomeScreenViewModel = hiltViewModel()
    val item = homeViewModel.listMovies.collectAsState().value[0]
    ComingPageItem(item)
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomePage() {
    val homeViewModel: HomeScreenViewModel = hiltViewModel()
    val movies = homeViewModel.listMovies.collectAsState()
    val imageBg = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

        ) {
        NowingMovieComp(movies.value)
        PopularMovieComp(movies.value)

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NowingMovieComp(listViewMoviesNowing: List<Movie>) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Now in Cinema", color = MaterialTheme.colorScheme.onBackground)
            TextButton(onClick = {}) {
                Text(text = "See all", color = Color.Red)
            }
        }
        val cp = LocalContext.current
        Row(modifier = Modifier.wrapContentSize()) {
            NowPlayingMoviesone(listViewMoviesNowing = listViewMoviesNowing) { movie ->
                Toast.makeText(cp, movie.avatar, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PopularMovieComp(listMovies: List<Movie>? = null) {
    val context = LocalContext.current
    PopularMovies(listMovies,
        onMovieClicked = {
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        },
        onMovieFavouriteClicked = {

        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PopularMovies(
    listMovies: List<Movie>? = null,
    onMovieClicked: (Movie) -> Unit, onMovieFavouriteClicked: (Movie) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        (listMovies)?.forEachIndexed { index, item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
                    .clickable { onMovieClicked(item) }
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = AppOptions.BASE_URL + item.avatar,
                        error = painterResource(
                            id = R.drawable.error_img
                        )
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(130.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .height(240.dp),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Text(
                            text = item.name.uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontFamily = balooFont,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark),
                            modifier = Modifier
                                .clickable { onMovieFavouriteClicked(item) }
                                .size(25.dp)
                                .align(Alignment.TopEnd),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = item.description,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                                append("${item.totalTime} | Khoa hoc vien tuong")
                            }
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(10.dp),
                                painter = painterResource(id = R.drawable.star_fill),
                                tint = Color.Yellow,
                                contentDescription = null
                            )
                            Text(
                                text = "9.6",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(16.dp),
                                painter = painterResource(id = R.drawable.timemanagement),
                                contentDescription = null
                            )
                            Text(
                                text = "${item.totalTime} phút",
//                                text = "115 phút",

                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val dateString = item.releaseDate
                            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                            // Parse the ISO 8601 string to ZonedDateTime
                            val zonedDateTime = ZonedDateTime.parse(dateString)

                            // Format the ZonedDateTime to a different format
                            val formattedDate = zonedDateTime.format(formatter)
                            Text(
//                        text = "${listViewMoviesNowing[page].releaseDate} ",\
                                text = formattedDate,

                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                    }
                }
            }
            Divider()

        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@OptIn(ExperimentalFoundationApi::class)

fun NowPlayingMoviesone(listViewMoviesNowing: List<Movie>, onMovieClicked: (Movie) -> Unit) {


    val pagerState =
        androidx.compose.foundation.pager.rememberPagerState(pageCount = { listViewMoviesNowing.size })
    androidx.compose.foundation.pager.HorizontalPager(
        verticalAlignment = Alignment.CenterVertically,
        state = pagerState,
        modifier = Modifier

            .wrapContentSize(),
        contentPadding = PaddingValues(horizontal = 60.dp),
        pageSpacing = 20.dp
    ) { page ->
        _imgBg.value=listViewMoviesNowing[page].avatar
        Column(
            modifier = Modifier
                .width(360.dp)
                .clickable { }

                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue



                    lerp(
                        start = ScaleFactor(1f, 0.85f),
                        stop = ScaleFactor(1f, 1f), fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also {
                        scaleX = it.scaleX
                        scaleY = it.scaleY
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//                rememberAsyncImagePainter(model = listMovies[page].avatar)
            val rotation = when {
                pagerState.currentPage == page + 1 -> -4f // Trang trước đó
                pagerState.currentPage == page -> 0f // Trang hiện tại
                else -> 4f // Các trang khác
            }
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .rotate(rotation)
                    .clip(RoundedCornerShape(16.dp))

                    .background(Color(0xFF792105)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(9.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .wrapContentSize()
                ) {
                    Log.d(
                        "ImageURL",
                        "Loading image from URL: ${listViewMoviesNowing[page].avatar}"
                    )
                    Image(

                        painter = rememberAsyncImagePainter(
                            model = AppOptions.BASE_URL + listViewMoviesNowing[page].avatar,
                            error = painterResource(
                                id = R.drawable.error_img
                            )
                        ),
                        contentDescription = "", contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(300.dp, 270.dp)
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = listViewMoviesNowing[page].name.uppercase(),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = balooFont,
                    letterSpacing = 1.sp,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.wrapContentSize()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(10.dp),
                            painter = painterResource(id = R.drawable.star_fill),
                            tint = Color.Yellow,
                            contentDescription = null
                        )
                        Text(
                            text = "9.6",
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.timemanagement),
                            contentDescription = null
                        )
                        Text(
                            text = "${listViewMoviesNowing[page].totalTime} phút",

                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val dateString = listViewMoviesNowing[page].releaseDate
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                        // Parse the ISO 8601 string to ZonedDateTime
                        val zonedDateTime = ZonedDateTime.parse(dateString)

                        // Format the ZonedDateTime to a different format
                        val formattedDate = zonedDateTime.format(formatter)
                        Text(
                            text = formattedDate,


                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    Canvas(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    ) {

                        drawLine(
                            color = Color.White,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = { onMovieClicked(listViewMoviesNowing[page]) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x4FCECECE)
                        ),
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Đặt vé", color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }

    }
}




