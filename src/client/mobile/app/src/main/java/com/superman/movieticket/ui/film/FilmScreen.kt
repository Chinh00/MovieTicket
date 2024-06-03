package com.superman.movieticket.ui.film

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.components.ScreenLoading
import com.superman.movieticket.ui.film.components.Banners
import com.superman.movieticket.ui.film.components.ItemMovie
import com.superman.movieticket.ui.film.control.FilmScreenViewModel
import com.superman.movieticket.ui.film.control.FilmScreenViewModelImpl
import com.superman.movieticket.ui.film.model.TabItem
import com.superman.movieticket.ui.theme.CustomColor4
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun FilmScreen() {



    TabItemComp()

}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabItemComp() {
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
        HorizontalPager(state = pager, modifier = Modifier.apply {
                                                                 fillMaxWidth().align(Alignment.CenterHorizontally)
        }) {
            when (selectedTabIndex) {
                0 -> ListMovieShowing()
                1 -> ListMovieComingSoon()
            }
        }
    }


}

val tabItems = listOf(
    TabItem("Đang chiếu", Icons.Default.PlayArrow, Icons.Default.ConnectedTv),
    TabItem("Sắp chiếu", Icons.Default.ChangeCircle, Icons.Filled.PlaylistAdd)
)





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListMovieShowing(

) {
    val filmScreenViewModel: FilmScreenViewModelImpl = hiltViewModel()

    LaunchedEffect(key1 = Unit) {
        filmScreenViewModel.getListFilmShowing()
    }


    val listMovies = filmScreenViewModel.listFilmShowing.collectAsState()


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScreenLoading(isLoading = filmScreenViewModel.apiState.collectAsState()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(listMovies.value) {idnex,item->
                    ItemMovie(item)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListMovieComingSoon(
) {
    val filmScreenViewModel: FilmScreenViewModelImpl = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        filmScreenViewModel.getListFilmComingSoon()
    }


    val listMovies = filmScreenViewModel.listFilmComingSoon.collectAsState()
    Log.d("Chinh", listMovies.toString())
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ScreenLoading(isLoading = filmScreenViewModel.apiState.collectAsState()) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(listMovies.value) {idnex,item->
                    ItemMovie(item)
                }
            }
        }
    }
}



