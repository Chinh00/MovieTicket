package com.superman.movieticket.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superman.movieticket.R
import com.superman.movieticket.core.view.FragmentBase
import com.superman.movieticket.databinding.FragmentHomeBinding
import com.superman.movieticket.ui.home.control.HomeFragmentModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class HomeFragment : FragmentBase<FragmentHomeBinding, HomeFragmentModelImpl>() {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModel() = HomeFragmentModelImpl::class.java
  
    override fun setupViews() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeContent()
            }
        }
    }

    override fun setupActions() {

    }


}

@Composable
fun HomeContent() {
    Box(modifier = Modifier.background(color = Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)

        ) {
            HeaderContent("Đông")
            Spacer(modifier = Modifier.height(13.dp))
            SearchField()
            Spacer(modifier = Modifier.height(13.dp))
            Banners()
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Category", style = TextStyle(
                        color = Color.White, fontSize = 20.sp
                    )
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "See All", style = TextStyle(
                            color = Color(0xFFFF9800),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            val listCategory = remember {
                mutableListOf(
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
        Text(text = name, color = Color.White, fontSize = 18.sp)

    }
    Spacer(modifier = Modifier.width(10.dp))

}

@Composable
//@Preview(showBackground = true)
fun CategoryItemPre() {
    CategoryItemComp("")
}

@Composable
fun HeaderContent(name: String) {

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Hello, $name", color = Color.White, fontSize = 20.sp)
            Text(
                text = "Let's book your favourite film",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier
                .shadow(elevation = 5.dp)
                .clip(
                    CircleShape
                )
        )
    }


}

@Composable
//@Preview(showBackground = true, backgroundColor = Long.MAX_VALUE)
fun HeaderContentPre() {
    HeaderContent("Đông")
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
            contentDescription = null, Modifier.size(24.dp)
        )
    }, placeholder = { Text(text = "Search", fontSize = 18.sp) }, trailingIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.microphone),
                contentDescription = null,
                Modifier.size(24.dp)
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
    HomeContent()
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