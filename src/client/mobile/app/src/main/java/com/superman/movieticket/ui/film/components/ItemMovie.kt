package com.superman.movieticket.ui.film.components

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.order.screening.ScreenActivity
import com.superman.movieticket.ui.order.screening.hooks.NavigateScreenActivity
import com.superman.movieticket.ui.theme.CustomColor4


@Composable

fun ItemMovie(
    m: Movie,
) {
    val context = LocalContext.current

    fun HandleTicket(id: String) {
        NavigateScreenActivity(
            context = context,
            movieId = id
        )
    }


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
                onClick = {
                    HandleTicket(m.id)
                }, text = "Đặt vé", modifier = Modifier.fillMaxWidth(),
                CustomColor4
            )

        }
    }
}
