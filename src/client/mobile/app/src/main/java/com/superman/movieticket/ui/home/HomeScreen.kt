package com.superman.movieticket.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.auth.hooks.NavigateLogin
import com.superman.movieticket.ui.components.ScreenLoading
import com.superman.movieticket.ui.detail.view.DetailActivity
import com.superman.movieticket.ui.order.screening.hooks.NavigateScreenActivity

import com.superman.movieticket.ui.theme.MyAppTheme
import com.superman.movieticket.ui.theme.balooFont
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow




@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {




    ScreenLoading(isLoading = homeScreenViewModel.apiState.collectAsState()) {
        MyAppTheme {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),

                    ) {
                    NowingMovieComp(homeScreenViewModel.listMovies1.collectAsState().value?: emptyList())
                    PopularMovieComp(homeScreenViewModel.listMovies1.collectAsState().value)

                }
            }

        }
    }

}

@Composable
fun rememberLifecycleOwner(context: Context): LifecycleOwner {
    return remember(context) {
        context as LifecycleOwner
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(movies: List<Movie>? = null) {


}








@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NowingMovieComp(listViewMoviesNowing: List<Movie>) {
    val loginSocialViewModel: LoginActivityViewModel = hiltViewModel()
    val login by loginSocialViewModel.isLogin.collectAsState(initial = null)
    val context = LocalContext.current
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = context.getString(R.string.now_playing),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall
            )
//            TextButton(onClick = {}) {
//                Text(text = "See all", color = Color.Red)
//            }
        }
        val cp = LocalContext.current
        fun HandleTicket(id: String) {
            NavigateScreenActivity(
                context = context,
                movieId = id
            )
        }
        Row(modifier = Modifier.wrapContentSize()) {
            NowPlayingMoviesone(listViewMoviesNowing = listViewMoviesNowing) { movie ->
                //Toast.makeText(cp, movie.avatar, Toast.LENGTH_SHORT).show()
                if (login != "true") {
                    NavigateLogin(context)
                } else {
                    HandleTicket(movie.id)

                }


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
            intent.putExtra("id", it.id)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

//            Log.w("idMV",it.id)
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

    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        (listMovies)?.forEachIndexed { index, item ->
            val isSaved = remember {
                mutableStateOf(false)
            }
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
                        model = AppOptions.BASE_URL + "/admin-api/image/" + item.avatar,
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
                            text = "${item.name ?: "Đang cập nhật"}".uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontFamily = balooFont,
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .fillMaxWidth(), color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            if (isSaved.value != true) Icons.Default.BookmarkBorder else Icons.Default.Bookmark,
                            modifier = Modifier
                                .clickable {
                                    onMovieFavouriteClicked(item)
                                    isSaved.value = !isSaved.value
                                }
                                .size(25.dp)
                                .align(Alignment.TopEnd),
                            contentDescription = null,
                            tint = if (isSaved.value != true) Color.Gray else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Text(
                        text = item.description ?: "Đang cập nhật",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                                append("${item.totalTime} | ${item.categories.joinToString(separator = " | ") { category -> category.name }}")
                                Log.d("ca", item.categories.toString())
                            }
                        },
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
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
                                text = "${item.totalTime} ${context.getString(R.string.txt_minutes)}",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                                    item.releaseDate,
                                    "dd/MM/yyyy"
                                ),

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
    val context = LocalContext.current

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
        Column(
            modifier = Modifier
                .width(360.dp)
                .height(450.dp)
                .clickable {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("id", listViewMoviesNowing[page].id)
//            Log.w("idMV",it.id)
                    context.startActivity(intent)
                }

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

                    .background(MaterialTheme.colorScheme.onPrimaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(9.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .wrapContentSize()
                ) {

                    Image(

                        painter = rememberAsyncImagePainter(
                            model = AppOptions.BASE_URL + "/admin-api/image/" + listViewMoviesNowing[page].avatar,
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
                val title = listViewMoviesNowing[page].name ?: "Đang cập nhật"
                Text(
                    text = "$title".uppercase(),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontFamily = balooFont,
                    letterSpacing = 1.sp,
                    maxLines = 1, overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.background,
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
                            color = MaterialTheme.colorScheme.background,
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
                            text = "${listViewMoviesNowing[page].totalTime} ${context.getString(R.string.txt_minutes)}",

                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                                listViewMoviesNowing[page].releaseDate,
                                "dd/MM/yyyy"
                            ),


                            color = MaterialTheme.colorScheme.background,
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
                        Text(
                            text = context.getString(R.string.txt_book_ticket),
                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }

    }

}




