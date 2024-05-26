package com.superman.movieticket.ui.home

import android.content.Intent
import android.graphics.Paint.Align
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.superman.movieticket.R
import com.superman.movieticket.ui.DetailsActivity
import com.superman.movieticket.ui.home.model.Movie
import com.superman.movieticket.ui.home.model.listMovies
import com.superman.movieticket.ui.shared.activity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF181824))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)

        ) {
//            HeaderContent("Đông")
            Spacer(modifier = Modifier.height(13.dp))
            SearchField()
            Spacer(modifier = Modifier.height(13.dp))
//            Banners()
//            Spacer(modifier = Modifier.height(13.dp))

            TabCustomComp()


        }
    }

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun TabCustomComp() {

    val tabItems = listOf("Now Playing", "Coming Soon")

    val pagerState = com.google.accompanist.pager.rememberPagerState()

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

                Tab(
                    text = {
                        Text(
                            text = title,
                            style = if (pagerState.currentPage == index) TextStyle(
                                color = Color.White,
                                fontSize = 18.sp
                            ) else TextStyle(color = Color.Black, fontSize = 16.sp)
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
        Spacer(modifier = Modifier.height(13.dp))
        HorizontalPager(
            state = pagerState, count = 2, modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF181824))
        ) { page ->
//            Text(text = tabItems[page], modifier = Modifier.padding(50.dp), color = Color.Black)
            when (pagerState.currentPage) {
                1 -> ComingUpScreenComp()

                0 -> NowPlayingScreenComp()

            }
        }
        Column {
            Row(modifier = Modifier.fillMaxSize()) {
                PopularMovieComp()

            }
        }

    }
}

@Composable
//@Preview(showSystemUi = true)
fun ComingUpScreenComp() {

    Box(modifier = Modifier.wrapContentSize()) {
        ComingUpMovies(
            onMovieFavourite = {

            },
            onMovieClicked = {

            }
        )
    }
}

@Composable

fun ComingUpMovies(onMovieClicked: (Movie) -> Unit, onMovieFavourite: (Movie) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {


        items(listMovies.size) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Transparent)
                    .padding(5.dp)


            ) {

                Image(
                    painter = painterResource(id = R.drawable.kungfu),
                    modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                    contentScale = ContentScale.Fit,
                    contentDescription = ""
                )
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null, tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = Color(0xBEE7E7E7)
                        )
                        .clickable { onMovieFavourite(listMovies[1]) }
                        .padding(20.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd)
                        .background(
                            color = Color(0xC30E0E69)
                        )
                        .size(60.dp)
                        .clickable { onMovieFavourite(listMovies[1]) }
                        .padding(20.dp), tint = Color.White
                )
            }
        }

    }
}

@Composable
//@Preview(showBackground = true)
fun ComingUpMoviesPre() {
    ComingUpMovies(
        onMovieClicked = { movie ->
            // Xử lý khi một bộ phim được nhấp vào
            // Đối số 'movie' là đối tượng Movie tương ứng
        },
        onMovieFavourite = { movie ->
            // Xử lý khi một bộ phim được đánh dấu là yêu thích
            // Đối số 'movie' là đối tượng Movie tương ứng
        }
    )


}

@Composable
//@Preview()
fun NowPlayingScreenComp() {
    val cp = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()


    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Category", style = TextStyle(
                        color = Color.White, fontSize = 16.sp
                    )
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "See All", style = TextStyle(
                            color = Color(0xFFFF9800),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        item {
            val listCategory = remember {
                mutableListOf(
                    "All",
                    "Drama",
                    "Romance",
                    "Comedy",
                    "Adventure",
                    "Action",
                    "Documentary",
                    "Fantasy",
                    "Horror",
                    "Mystery",
                    "Romance",
                    "Science Fiction"
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                LazyRow {
                    items(listCategory.size) {
                        CategoryItemComp(name = listCategory[it])
                    }
                }
            }

            Spacer(modifier = Modifier.height(13.dp))
        }
        item {
            Row(modifier = Modifier.wrapContentSize()) {
                NowPlayingMovies { movie ->
                    Toast.makeText(cp, movie.avatar, Toast.LENGTH_SHORT).show()
                    val intent = Intent(cp,DetailsActivity::class.java)
                    cp.startActivity(intent)
                }
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular Movies", style = TextStyle(
                        color = Color.White, fontSize = 16.sp
                    )
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "See All", style = TextStyle(
                            color = Color(0xFFFF9800),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }


        items(listMovies.size) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(cp, DetailsActivity::class.java)
                        cp.startActivity(intent)
                    }
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(listMovies[it].avatar, error = painterResource(
                        id = R.drawable.poster_payoff_aquaman_6_1_
                    )),
                    contentDescription = null, modifier = Modifier
                        .width(150.dp)
                        .clip(
                            RoundedCornerShape(13.dp)
                        )
                        .height(200.dp), contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Text(
                            text = listMovies[it].title.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Icon(painter = painterResource(id = R.drawable.bookmark),
                            modifier = Modifier
                                .clickable {
                                    Toast
                                        .makeText(cp, "Thêm mục iu thích", Toast.LENGTH_SHORT)
                                        .show()
                                }//Onclicked Favoureite
                                .size(25.dp)
                                .align(Alignment.TopEnd),
                            contentDescription = null,
                            tint = Color.White)
                    }
                    Row {
                        Text(
                            text = listMovies[it].description,
                            fontSize = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                    var rating by remember {
                        mutableStateOf(3.2f)
                    }
                    Row {
                        RatingBar(
                            value = rating,
                            style = RatingBarStyle.Fill(),
                            onValueChange = {
                                rating = rating
                            },
                            onRatingChanged = {
                                Log.d("TAG", "onRatingChanged: $it")
                            }

                        )

                    }
                    Row {
                        Text(text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("${listMovies[it].duration} | Khoa hoc vien tuong")
                            }
                        })

                    }
                    Row {
                        Text(
                            text = "CGV", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))

                        Text(
                            text = "Cinema", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))

                        Text(
                            text = "BHD Star", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )

                    }
                }

            }
            Spacer(modifier = Modifier.height(13.dp))
        }


    }



}

@Composable
fun PopularMovieComp() {
    PopularMovies(
        onMovieClicked = {

        },
        onMovieFavouriteClicked = {

        }
    )
}

@Composable
fun PopularMovies(onMovieClicked: (Movie) -> Unit, onMovieFavouriteClicked: (Movie) -> Unit) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listMovies.size) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bietdoidanhthue),
                    contentDescription = null, modifier = Modifier
                        .width(150.dp)
                        .clip(
                            RoundedCornerShape(13.dp)
                        )
                        .height(200.dp), contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        Text(
                            text = listMovies[it].title.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Icon(painter = painterResource(id = R.drawable.bookmark),
                            modifier = Modifier
                                .clickable { onMovieFavouriteClicked(listMovies[it]) }
                                .size(25.dp)
                                .align(Alignment.TopEnd),
                            contentDescription = null,
                            tint = Color.Black)
                    }
                    Row {
                        Text(
                            text = listMovies[it].description,
                            fontSize = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                    var rating by remember {
                        mutableStateOf(3.2f)
                    }
                    Row {
                        RatingBar(
                            value = rating,
                            style = RatingBarStyle.Fill(),
                            onValueChange = {
                                rating = rating
                            },
                            onRatingChanged = {
                                Log.d("TAG", "onRatingChanged: $it")
                            }

                        )

                    }
                    Row {
                        Text(text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.Black)) {
                                append("${listMovies[it].duration} | Khoa hoc vien tuong")
                            }
                        })

                    }
                    Row {
                        Text(
                            text = "CGV", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))

                        Text(
                            text = "Cinema", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )
                        Spacer(modifier = Modifier.width(13.dp))

                        Text(
                            text = "BHD Star", color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray, RoundedCornerShape(20.dp))
                                .padding(5.dp)
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PopularMoviesPre() {
    PopularMovieComp()
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun NowPlayingMovies(onMovieClicked: (Movie) -> Unit) {


    val pagerState = rememberPagerState(pageCount = {
        listMovies.size
    })
    androidx.compose.foundation.pager.HorizontalPager(
        verticalAlignment = Alignment.CenterVertically,
        state = pagerState,
        modifier = Modifier

            .wrapContentSize(),
        contentPadding = PaddingValues(horizontal = 60.dp),
        pageSpacing = 10.dp
    ) { page ->
        Column(
            modifier = Modifier
                .wrapContentSize()
                .clickable { onMovieClicked(listMovies[page]) }

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
                Log.d("ImageURL", "Loading image from URL: ${listMovies[page].avatar}")
                Image(

                    painter = painterResource(id = R.drawable.poster_payoff_aquaman_6_1_),
                    contentDescription = "", contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(300.dp)
                        .width(250.dp)
                        .clip(RoundedCornerShape(15.dp))
                )

            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = listMovies[page].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold, style = TextStyle(
                    letterSpacing = 2.sp,
                ),
                color = Color.White, modifier = Modifier.wrapContentSize()
            )
        }

    }
}


@Composable
fun CategoryItemComp(name: String) {
    val isChecked = remember {
        mutableStateOf(false)
    }
    val nametitle = remember {
        mutableStateOf("")
    }
    var context1 = LocalContext.current
    val bg = if (isChecked.value) Color(0xFFFF9800) else Color.Transparent
    val borderColor = if (isChecked.value) Color.Transparent else Color(0xFFFF9800)
    TextButton(
        onClick = {

            isChecked.value = !isChecked.value

        },
        modifier = Modifier
            .background(color = bg, shape = RoundedCornerShape(20.dp))
            .border(
                1.dp, borderColor,
                RoundedCornerShape(20.dp)
            )


    ) {
        Text(text = name, color = Color.White, fontSize = 14.sp)

    }
    Spacer(modifier = Modifier.width(5.dp))

}

@Composable
//@Preview(showBackground = true)
fun CategoryItemPre() {
    CategoryItemComp("")
}

//@Composable
//fun HeaderContent(name: String) {
//
//    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
//        Column {
//            Text(text = "Hello, $name", color = Color.White, fontSize = 18.sp)
//            Text(
//                text = "Let's book your favourite film",
//                color = Color.Gray,
//                fontSize = 16.sp
//            )
//        }
//        Image(
//            painter = painterResource(id = R.drawable.avatar),
//            contentDescription = null,
//            modifier = Modifier
//                .shadow(elevation = 5.dp)
//                .clip(
//                    CircleShape
//                )
//                .size(50.dp)
//        )
//    }
//
//
//}

@Composable
//@Preview(showBackground = true, backgroundColor = Long.MAX_VALUE)
fun HeaderContentPre() {
//    HeaderContent("Đông")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showBackground = true)
fun SearchField() {
    val containerColor = Color(0x86424242)
    TextField(value = "", onValueChange = {}, modifier = Modifier
        .fillMaxWidth()
        .clip(
            RoundedCornerShape(15.dp)
        ), leadingIcon = {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = null, Modifier.size(20.dp)
        )
    }, placeholder = { Text(text = "Search", fontSize = 18.sp) }, trailingIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.microphone),
                contentDescription = null,
                Modifier.size(20.dp)
            )
        }
    }, colors = TextFieldDefaults.colors(
        focusedContainerColor = containerColor,
        unfocusedContainerColor = containerColor,
        disabledContainerColor = containerColor,
        unfocusedIndicatorColor = Color.Transparent,
    )
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeContentPre() {
    HomeScreen()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banners() {
    val list_banner = listOf(
        BannerItem(R.drawable.kungfu, "Kung FU PanDa"),
        BannerItem(R.drawable.poster, "THành phố ngủ gật"),
        BannerItem(R.drawable.poster1, "Kẻ Ăn Hồn"),

        )

    val pagerState = rememberPagerState(

        initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { list_banner.size }
    )
    var bannerIndex by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex = page
        }
    }
    //tự động kéo
    LaunchedEffect(Unit) {
        while (true) {
            delay(2_000)
            tween<Float>(1500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        HorizontalPager(
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp)), state = pagerState
        ) { index ->
            BannerItemContent(title = list_banner[index].title, img = list_banner[index].img)
        }

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            repeat(list_banner.size) {
                val h = 12.dp
                val w = if (it == bannerIndex) 28.dp else 12.dp
                val c = if (it == bannerIndex) Color.Yellow else Color.Gray

                Surface(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(w, h)
                        .clip(RoundedCornerShape(20.dp)), color = c
                ) {


                }
            }
        }
    }


}

@Composable
fun BannerItemContent(title: String, img: Int) {
    Box(
        Modifier
            .paint(painterResource(id = img), contentScale = ContentScale.FillBounds)
            .fillMaxHeight()
            .fillMaxWidth(), contentAlignment = Alignment.CenterEnd
    ) {


        Row(
            Modifier.padding(start = 5.dp, end = 20.dp, top = 70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(text = title, color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(10.dp))


            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,

                    ), modifier = Modifier.wrapContentWidth(), shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null)
                Text(text = " Watch Now")
            }
        }


    }
}

//@Composable
//@Preview(showBackground = true)
//fun BannerItemContentPre() {
//    BannerItemContent("", 0)
//}

data class BannerItem(
    @DrawableRes val img: Int,
    val title: String
)