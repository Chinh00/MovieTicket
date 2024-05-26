package com.superman.movieticket.ui.favourite

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.ui.DetailsActivity
import com.superman.movieticket.ui.home.model.Movie
import com.superman.movieticket.ui.home.model.listMovies

@Composable
fun FavouriteScreen() {
    var  c= LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp).padding(start = 5.dp)
                )
                Text(
                    text = "Favourite",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(color = Color(0xE21D49A3))
            ){

            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(listMovies.size) {
                    FavouriteItem(c,listMovies[it])

                }
            }
        }
    }
}

@Composable
fun FavouriteItem(c:Context,m:Movie) {
    if (m.id.toInt() % 2 != 0) {
        Row(
            modifier = Modifier
                .fillMaxWidth().clickable {
                    //on clicked MivieDetail

                    val intent = Intent(c,DetailsActivity::class.java)
                }
                .height(150.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (s, e, t, b) = createRefs()
                Row(modifier = Modifier
                    .constrainAs(s) {
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround) {

                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))

                            .size(300.dp, 200.dp), contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = R.drawable.posterbanner1),
                        contentDescription = null
                    )


                    Text(
                        text = "${m.id}",
                        color = Color.White,
                        modifier = Modifier.wrapContentSize(),
                        fontSize = 30.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = m.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .constrainAs(e) {
                            bottom.linkTo(s.bottom)
                            end.linkTo(s.end)
                        }
                        .padding(end = 40.dp, bottom = 10.dp)
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (s, e, t, b) = createRefs()

                Row(modifier = Modifier
                    .constrainAs(e) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Text(
                        text = "${m.id}",
                        color = Color.White,
                        modifier = Modifier.wrapContentSize(),
                        fontSize = 30.sp, textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))

                            .size(300.dp, 200.dp),
                        painter = painterResource(id = R.drawable.posterbanner1), contentScale = ContentScale.FillBounds,
                        contentDescription = null
                    )


                }
                Text(
                    text = m.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .constrainAs(s) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(bottom = 50.dp, start = 50.dp))
            }
        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun FavouriteScreenPre() {
    FavouriteScreen()
}

