package com.superman.movieticket.ui.home


import android.util.Log
import android.util.Size

import android.widget.Toast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.PlayArrow


import androidx.compose.material3.Icon

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.home.control.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.superman.movieticket.ui.theme.MyAppTheme
import kotlinx.coroutines.flow.collect
import java.time.LocalDateTime
import java.util.Date

@Composable
fun HomeScreen() {
    MyAppTheme {
        HomeContent()

    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeContent() {
    val homeViewModel: HomeScreenViewModel = hiltViewModel()
    var valueSearch by rememberSaveable {
        mutableStateOf("")
    }
    val scrollVertical = rememberScrollState()

    var searchScreen by rememberSaveable {
        mutableStateOf(0)
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (s, t, b, e) = createRefs()
        Column(modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .constrainAs(e) {
                top.linkTo(parent.top)
            }) {
            TabRow(
                selectedTabIndex = searchScreen,
                containerColor = Color.White,
                contentColor = Color.Blue
            ) {
                tabsList.forEachIndexed { index, item ->
                    Tab(
                        selected = index == searchScreen,
                        onClick = { searchScreen = index },
                        text = {
                            Text(
                                text = "${item.title}"
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == searchScreen) item.selectIcon else item.unSelectIcon,
                                contentDescription = item.title
                            )
                        })
                }
            }
        }
        Column(modifier = Modifier
            .constrainAs(t) {
                top.linkTo(e.bottom)
            }
            .verticalScroll(scrollVertical)
            .fillMaxSize()) {
            when (searchScreen) {
                0 -> {
                    HomePage(homeViewModel)
                }

                1 -> {
                    Log.d("Screen", "Search Scereen")
                    ComingPage()
                }
            }


        }
    }
}

@Composable
fun ComingPage() {
    val homeScreenModel: HomeScreenViewModel = hiltViewModel()
    val movies = homeScreenModel.listMovies.collectAsState()

    Log.d("Chinh", movies.value.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        movies.value.forEach {
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
                    Text("${m.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
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
                            style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold,
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
@Preview(showSystemUi = true)
fun ComingPagePre() {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val item = homeScreenViewModel.listMovies.collectAsState().value[0]
    LaunchedEffect(Unit) {

    }
    ComingPageItem(item)
}


@Composable
fun HomePage(homeScreenModel: HomeScreenViewModel) {

    val movies = homeScreenModel.listMovies.collectAsState()

    LaunchedEffect(Unit) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
//
//
        NowingMovieComp(movies.value)

        PopularMovieComp(movies.value)
    }
}

@Composable
fun NowingMovieComp(listViewMoviesNowing: List<Movie>) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Now in Cinema", color = Color.White)
            TextButton(onClick = {}) {
                Text(text = "See all", color = Color.Red)
            }
        }
//        Row() {
//            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//                items(listMovies.size) {
//                    Column(modifier = Modifier.size(100.dp, 185.dp)) {
////                    rememberAsyncImagePainter(listMovies[it].avatar)
//                        Image(
//                            painter = painterResource(id = R.drawable.poster_payoff_aquaman_6_1_),
//                            modifier = Modifier.clip(
//                                RoundedCornerShape(8.dp)
//                            ),
//                            contentDescription = null
//                        )
//
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            modifier = Modifier.width(100.dp)
//                        ) {
//                            Text(text = "IMDB", fontSize = 14.sp, color = Color.White)
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    Icons.Default.Star,
//                                    tint = Color.Yellow,
//                                    contentDescription = null
//                                )
//                                Text(text = "8.5", color = Color.White)
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
        var cp = LocalContext.current
        Row(modifier = Modifier.wrapContentSize()) {
            NowPlayingMoviesone(listViewMoviesNowing = listViewMoviesNowing) { movie ->
                Toast.makeText(cp, movie.avatar, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun PopularMovieComp(listMovies: List<Movie>? = null) {
    PopularMovies(listMovies,
        onMovieClicked = {

        },
        onMovieFavouriteClicked = {

        }
    )
}


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
                    .padding(20.dp)
                    .clickable { onMovieClicked(item) }
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.bietdoidanhthue),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .height(170.dp)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(6.dp)),
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
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(), color = Color.White
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
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("${item.totalTime} | Khoa hoc vien tuong")
                            }
                        }
                    )
                    Row {
                        Text(
                            text = "CGV",
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))
                        Text(
                            text = "Cinema",
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))
                        Text(
                            text = "BHD Star",
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                    }
                }
            }
        }

    }
}


data class TabItem(
    val title: String,
    val unSelectIcon: ImageVector,
    val selectIcon: ImageVector
)

val tabsList = listOf(
    TabItem(
        "Đang Chiếu",
        unSelectIcon = Icons.Filled.PlayArrow,
        selectIcon = Icons.Default.HdrOnSelect
    ),
    TabItem(
        "Sắp Chiếu",
        unSelectIcon = Icons.Filled.PlayArrow,
        selectIcon = Icons.Default.HdrOnSelect
    )

)

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
        contentPadding = PaddingValues(horizontal = 80.dp),
        pageSpacing = 20.dp
    ) { page ->
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clickable { onMovieClicked(listViewMoviesNowing[page]) }

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
                }
//            ,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//                rememberAsyncImagePainter(model = listMovies[page].avatar)
            val rotation = when {
                pagerState.currentPage == page + 1 -> -4f // Trang trước đó
                pagerState.currentPage == page -> 0f // Trang hiện tại
                else -> 4f // Các trang khác
            }
            Box(
                modifier = Modifier
                    .rotate(rotation)
                    .clip(RoundedCornerShape(16.dp))
                    .wrapContentSize()
            ) {
                Log.d("ImageURL", "Loading image from URL: ${listViewMoviesNowing[page].avatar}")
                Image(

                    painter = painterResource(id = R.drawable.poster_payoff_aquaman_6_1_),
                    contentDescription = "", contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .wrapContentSize()
                )

            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = listViewMoviesNowing[page].name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, style = TextStyle(
                    letterSpacing = 2.sp,
                ),
                color = Color.White, modifier = Modifier.wrapContentSize()
            )
        }

    }
}



