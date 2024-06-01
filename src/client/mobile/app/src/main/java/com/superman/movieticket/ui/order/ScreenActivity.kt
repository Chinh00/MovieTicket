package com.superman.movieticket.ui.order

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showSystemUi = true)
fun ScreensComp() {
    val dateScrollState = rememberScrollState()
    val selectedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }
    val today = LocalDate.now()
    Column {
        Row(
            modifier = Modifier
                .padding(top = 5.dp, start = 5.dp)

                .horizontalScroll(dateScrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (0..14).forEach { i ->
                val date = today.plusDays(i.toLong())
                DateComp(date = date, isSelected = selectedDate.value == date) {
                    selectedDate.value = it
                }
            }
        }
        LazyVerticalGrid(
            modifier = Modifier.padding(vertical = 5.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(100) { index ->
                ScreenItemComp("SCREEN$index")
            }
        }
    }
//    Row{
//        ScreenItemComp()
//    }
}

@Composable
//@Preview(showSystemUi = true)
fun ScreenItemComp(nameScreen: String) {
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .shadow(4.dp, spotColor = Color.Green)

            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .size(90.dp, 110.dp)
                .padding(3.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$nameScreen".uppercase(),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "11:50 - 13:30 ",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "113/200 ghế ", color = MaterialTheme.colorScheme.background)

            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateComp(
    date: LocalDate, isSelected: Boolean = false,
    onClick: (LocalDate) -> Unit = {}
) {
    val color = when {
        isSelected -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.background
    }
    val textBg = when {
        isSelected -> MaterialTheme.colorScheme.background
        else ->  MaterialTheme.colorScheme.onBackground
    }

    Surface(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick(date) }, shape = RoundedCornerShape(8.dp), color = color
    ) {
        Column(
            modifier = Modifier.padding(15.dp,5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            val ngaytrongtuan = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            Text(
                text ="${ngaytrongtuan}" ,
                style = MaterialTheme.typography.titleMedium,color=textBg
            )
            val ngaytrongthang = date.dayOfMonth
            val ntt =  if(ngaytrongthang>=10) ngaytrongthang.toString() else "0$ngaytrongthang"

            Text(text = ntt,color=textBg, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)


        }
    }
}