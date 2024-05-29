@file:OptIn(ExperimentalComposeUiApi::class)

package com.superman.movieticket.ui.home

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.superman.movieticket.R
import com.superman.movieticket.ui.home.model.Movie
import com.superman.movieticket.ui.home.model.listMovies
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    HomeContent()
}

@Composable
@Preview(showSystemUi = true)
fun HomeContent() {
    var valueSearch by rememberSaveable {
        mutableStateOf("")
    }
    var searchScreen by rememberSaveable {
        mutableStateOf(0)
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (s, t, b, e) = createRefs()
        Column(modifier = Modifier.constrainAs(s) {
            top.linkTo(parent.top)
        }) {
            SearchBox { s, isSubmited ->
                searchScreen = isSubmited
                valueSearch = s
            }
        }
        Column(modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .constrainAs(e) {
                top.linkTo(s.bottom)
            }) {
            TabRow(selectedTabIndex = searchScreen, backgroundColor = Color.White, contentColor = Color.Blue) {
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
        Column(modifier = Modifier.constrainAs(t) {
            top.linkTo(e.bottom)
        }) {
            when (searchScreen) {
                0 -> {
                    HomePage()
                }

                1 -> {
                    Log.d("Screen", "Search Scereen")
                    SearchPage()
                }
            }


        }
    }
}

@Composable
fun SearchPage() {
    var isLoading by remember { mutableStateOf(true) }

    // Simulate loading data with a delay
    LaunchedEffect(Unit) {
        delay(2000)  // Giả lập tải dữ liệu trong 2 giây
        isLoading = false
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "SearchScreen")
        }
    }
}

@Composable
fun HomePage() {
    var isLoading by remember { mutableStateOf(true) }

    // Simulate loading data with a delay
    LaunchedEffect(Unit) {
        delay(2000)  // Giả lập tải dữ liệu trong 2 giây
        isLoading = false
    }

    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            androidx.compose.material3.CircularProgressIndicator(
//                modifier = Modifier.width(64.dp),
//                color = androidx.compose.material3.MaterialTheme.colorScheme.secondary,
//                trackColor = Color.LightGray,
//            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NowingMovieComp()
            PopularMovieComp()
        }
    }
}

@Composable
fun NowingMovieComp() {
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
        Row() {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(listMovies.size) {
                    Column(modifier = Modifier.size(100.dp, 185.dp)) {
//                    rememberAsyncImagePainter(listMovies[it].avatar)
                        Image(
                            painter = painterResource(id = R.drawable.poster_payoff_aquaman_6_1_),
                            modifier = Modifier.clip(
                                RoundedCornerShape(8.dp)
                            ),
                            contentDescription = null
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.width(100.dp)
                        ) {
                            Text(text = "IMDB", fontSize = 14.sp, color = Color.White)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Star,
                                    tint = Color.Yellow,
                                    contentDescription = null
                                )
                                Text(text = "8.5", color = Color.White)
                            }

                        }
                    }
                }
            }
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
        items(listMovies.size) { index ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable { onMovieClicked(listMovies[index]) }
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.bietdoidanhthue),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .height(200.dp)
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
                            text = listMovies[index].title.uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(), color = Color.White
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark),
                            modifier = Modifier
                                .clickable { onMovieFavouriteClicked(listMovies[index]) }
                                .size(25.dp)
                                .align(Alignment.TopEnd),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = listMovies[index].description,
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    var rating by remember { mutableStateOf(3.2f) }
                    RatingBar(
                        value = rating,
                        style = RatingBarStyle.Fill(),
                        onValueChange = {
                            rating = it
                        },
                        onRatingChanged = {
                            Log.d("TAG", "onRatingChanged: $it")
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) {
                                append("${listMovies[index].duration} | Khoa hoc vien tuong")
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
        item {
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { /* Xử lý khi nút được nhấn */ },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
            ) {
                Text(text = "SAVE")
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

@Composable
@Preview(showSystemUi = true)
fun NowingMoviePre() {
    NowingMovieComp()

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(onSearch: (String, Int) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var txtString = remember {
        mutableStateOf("")
    }
    TextField(value = txtString.value,
        onValueChange = {
            txtString.value = it
        },
        placeholder = { Text(text = "Search", color = Color.Gray) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.microphone),
                contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Gray
            )
        }, keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ), keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(txtString.value, 2)
                keyboardController?.hide()
            }

        ),
        colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
            containerColor = Color.LightGray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White

        ),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
    )
}

@Composable
@Preview(showSystemUi = true)
fun SearchBoxPre() {
    var valueSearch = remember {
        mutableStateOf("")
    }
    SearchBox { s, isSubmited ->
        if (isSubmited == 1) {
            Log.d("Screen", "Search Scereen")
        }
    }
    Box(modifier = Modifier.clip(
        Path().apply {
            moveTo(0f, 0f)
            quadraticBezierTo(size.width / 2, size.height / 2, size.width, 0f)
        }
    )) {
        // Optional content within the Box (can be empty)
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