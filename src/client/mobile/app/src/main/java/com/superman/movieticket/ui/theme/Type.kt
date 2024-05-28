package com.superman.movieticket.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.superman.movieticket.R
import com.superman.movieticket.ui.profile.Text

val balooTamma = FontFamily(
    Font(resId = R.font.balootamma2_bold, FontWeight.Bold),

    Font(resId = R.font.balootamma2_medium, FontWeight.Medium),
    Font(resId = R.font.balootamma2_regular, FontWeight.Normal),
    Font(resId = R.font.balootamma2_semibold, FontWeight.SemiBold),
    Font(resId = R.font.balootamma2_extrabold, FontWeight.ExtraBold),

    )
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = balooTamma,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h2 = TextStyle(
        fontFamily = balooTamma,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    h3 = TextStyle(
        fontFamily = balooTamma,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )

)

