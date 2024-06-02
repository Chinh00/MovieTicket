package com.superman.movieticket.ui.film.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.superman.movieticket.R
import kotlinx.coroutines.delay


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