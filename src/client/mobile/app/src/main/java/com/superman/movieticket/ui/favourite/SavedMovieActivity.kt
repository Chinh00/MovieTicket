package com.superman.movieticket.ui.favourite


import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.superman.movieticket.R
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.home.control.HomeScreenModel
import com.superman.movieticket.ui.theme.MyAppTheme

class SavedMovieActivity : ComponentActivity() {
    private fun isDarkModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkMode = isDarkModeEnabled()
        setContent {
            MyAppTheme(isDarkMode) {
                SavedMovieComp()
            }
        }
    }

}


@Composable
fun SavedMovieComp() {
    val homeScreenModel = HomeScreenModel()
    val movies = homeScreenModel.listMovies
    val scrollVertical = rememberScrollState()

    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        val (t, b) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .constrainAs(t) {
                    top.linkTo(parent.top)
                },
            verticalAlignment = Alignment.CenterVertically

        ) {
            androidx.compose.material.Icon(
                Icons.Outlined.KeyboardArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(36.dp)
            )
            androidx.compose.material.Text(
                text = "Saved Movies",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold
            )
        }
        Column(modifier = Modifier
            .constrainAs(b) {
                top.linkTo(t.bottom)
            }
            .verticalScroll(scrollVertical), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            (1..10).forEach {
                SavedMovieItemComp(movies.value[1]) {

                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)

fun SavedMoviePre() {
    SavedMovieComp()
}

@Composable
fun SavedMovieItemComp(
    m: Movie,
    onclickItem: (Any?) -> Unit
) {
    val colorText = Color.White
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(105.dp)
            .clickable { onclickItem(m) }
    ) {

        androidx.compose.foundation.Image(
            painter = painterResource(id = R.drawable.exhuma),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(horizontal = 10.dp)
                .height(105.dp)
        ) {
            Text(
                text = "Quật Mộ Trùng Ma",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "Ngày phát hành: 23-04-24",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "1h30’ - 3D",
                        color =MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = "Mô tả",
                        color = colorText,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.background(Color(0xFFCDDC39)),
                        fontSize = 14.sp
                    )
                }
            }

        }

    }

}

@Composable

@Preview(showSystemUi = true)
fun SavedMovieItemPre() {
//    MyOrderItemComp()
}

val balooTamma = FontFamily(
    Font(resId = R.font.balootamma2_bold, FontWeight.Bold),

    Font(resId = R.font.balootamma2_medium, FontWeight.Medium),
    Font(resId = R.font.balootamma2_regular, FontWeight.Normal),
    Font(resId = R.font.balootamma2_semibold, FontWeight.SemiBold),
    Font(resId = R.font.balootamma2_extrabold, FontWeight.ExtraBold),

    )
