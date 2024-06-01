package com.superman.movieticket.ui.film

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.ConnectedTv
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.film.control.FilmScreenViewModel
import com.superman.movieticket.ui.film.control.FilmScreenViewModelImpl
import com.superman.movieticket.ui.theme.CustomColor4
import kotlinx.coroutines.delay


@Composable
fun FilmScreen() {
    val filmScreenViewModel: FilmScreenViewModelImpl = hiltViewModel()

    //list film showing
    val listFilmShowing = filmScreenViewModel.listFilmShowing.collectAsState()
    // list film ComingSoon
    val listFilmComingSoon = filmScreenViewModel.listFilmComingSoon.collectAsState()


    TabItemComp(listFilmShowing.value,listFilmComingSoon.value)

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabItemComp(listMovieNowing: List<Movie>,listMovieComing: List<Movie>) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val pager = rememberPagerState(pageCount = { tabItems.size })
    LaunchedEffect(selectedTabIndex) {
        pager.animateScrollToPage(selectedTabIndex)


    }

    LaunchedEffect(pager.currentPage, pager.isScrollInProgress) {
        if (!pager.isScrollInProgress) {
            selectedTabIndex = pager.currentPage
        }


    }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex,
            divider = {
                Divider(color = Color.Black)
            }) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = item.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Light
                        )
                    },
                    selectedContentColor = Color(0xFF7A6413),
                    unselectedContentColor = Color(0xFF7A6413)


                )
            }
        }
        Banners()
        HorizontalPager(state = pager, modifier = Modifier.fillMaxWidth()) {
            when (selectedTabIndex) {
                0 -> NowingMovieComp(listMovieNowing)
                1 -> ComingMovieComp(listMovieComing)

            }
        }
    }


}

val tabItems = listOf(
    TabItem("Đang chiếu", Icons.Default.PlayArrow, Icons.Default.ConnectedTv),
    TabItem("Sắp chiếu", Icons.Default.ChangeCircle, Icons.Filled.PlaylistAdd)

)

data class TabItem(
    val name: String,
    val iconSelected: ImageVector,
    val iconUnSelected: ImageVector,

    )


@Composable

fun ItemMovie(m:Movie,onClick:(Movie)->Unit) {
    Row(modifier = Modifier
        .padding(horizontal = 10.dp, vertical = 8.dp)
        .clickable { }
        .fillMaxWidth()) {
        Column(modifier = Modifier.size(140.dp, 180.dp)) {
            ConstraintLayout {
                val t = createRef()

                Image(painter = rememberAsyncImagePainter(model = m.avatar),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .constrainAs(t) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                    contentDescription = null
                )

                Text(
                    text = "T16", color = MaterialTheme.colorScheme.background, modifier = Modifier
                        .background(
                            Color.Blue,
                            RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                        )
                        .padding(7.dp)
                )


            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = m.name.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Thời lượng: 126 phút", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Khởi chiếu: D-6", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Thể loại: gia đình", style = MaterialTheme.typography.bodyMedium)
            CustomButton(
                onClick = { onClick(m) }, text = "Đặt vé", modifier = Modifier.fillMaxWidth(),
                CustomColor4
            )

        }
    }
}

@Composable
fun NowingMovieComp(listMovieNowing:List<Movie>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(listMovieNowing) {idnex,item->
            ItemMovie(item){

            }
        }
    }
}
@Composable
fun ComingMovieComp(listMovieComing:List<Movie>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(listMovieComing) {idnex,item->
            ItemMovie(item){

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banners() {
    val list_banner = listOf(
        R.drawable.kungfu,
        R.drawable.doremon,
        R.drawable.coba,

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
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth()

    ) {
        HorizontalPager(
            modifier = Modifier
                .height(150.dp)
                , state = pagerState
        ) { index ->
            Image(painter = painterResource(id =list_banner[index]), modifier = Modifier.fillMaxWidth(), contentDescription = null, contentScale = ContentScale.FillBounds)
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