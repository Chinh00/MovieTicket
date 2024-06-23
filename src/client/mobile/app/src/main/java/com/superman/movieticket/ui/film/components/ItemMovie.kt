package com.superman.movieticket.ui.film.components

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.superman.movieticket.R
import com.superman.movieticket.core.config.AppOptions
import com.superman.movieticket.domain.entities.Movie
import com.superman.movieticket.infrastructure.utils.DatetimeHelper
import com.superman.movieticket.ui.auth.control.LoginActivityViewModel
import com.superman.movieticket.ui.auth.hooks.NavigateLogin
import com.superman.movieticket.ui.components.CustomButton
import com.superman.movieticket.ui.detail.view.DetailActivity
import com.superman.movieticket.ui.order.screening.ScreenActivity
import com.superman.movieticket.ui.order.screening.hooks.NavigateScreenActivity
import com.superman.movieticket.ui.theme.CustomColor4
import com.superman.movieticket.ui.theme.CustomColor6
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun ItemMovie(
    m: Movie,
) {
    val context = LocalContext.current
    val loginSocialViewModel: LoginActivityViewModel = hiltViewModel()
    val login by loginSocialViewModel.isLogin.collectAsState(initial = null)
    fun HandleTicket(id: String) {
        NavigateScreenActivity(
            context = context,
            movieId = id
        )
    }


    Row(modifier = Modifier
        .padding(horizontal = 10.dp, vertical = 8.dp)
        .clickable {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id",m.id)
//            Log.w("idMV",it.id)
            context.startActivity(intent)
        }
        .fillMaxWidth()) {
        Column(modifier = Modifier.size(170.dp, 270.dp)) {
            ConstraintLayout {
                val t = createRef()

                Image(painter = rememberAsyncImagePainter(
                    model = AppOptions.BASE_URL + "/admin-api/image" + m.avatar,
                    error = painterResource(
                        id = R.drawable.error_img
                    )
                ),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .constrainAs(t) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
                        .fillMaxSize(),
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
        val colorText = MaterialTheme.colorScheme.onBackground
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = m.name.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, color = colorText
            )
            Text(
                text = "${context.getString(R.string.txt_duration)}: ${m.totalTime} ${
                    context.getString(
                        R.string.txt_minutes
                    )
                }",
                style = MaterialTheme.typography.bodyMedium, color = colorText
            )

            Text(
                text = "${context.getString(R.string.txt_release_date)}: ${
                    DatetimeHelper.ConvertISODatetimeToLocalDatetime(
                        m.releaseDate,
                        "dd/MM/yyyy"
                    )
                }", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${context.getString(R.string.txt_category)}: ${m.categories.joinToString(", ") { it.name }}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorText,
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedButton(
                onClick = {
                          if(login!="true"){
                              NavigateLogin(context)
                          }else{
                              HandleTicket(m.id)
                          }
                }, colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,

                    ), modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    text = context.getString(R.string.txt_book_ticket),
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

        }
    }
    Divider()

}
