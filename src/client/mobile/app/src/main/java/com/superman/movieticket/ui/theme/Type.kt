package com.superman.movieticket.ui.theme



import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.superman.movieticket.R

val balooFont = FontFamily(
    Font(resId = R.font.balootamma2_bold, weight = FontWeight.Bold),
    Font(resId =R.font.balootamma2_medium, weight = FontWeight.Medium),
    Font(resId =R.font.balootamma2_regular, weight = FontWeight.Normal),
    Font(resId =R.font.balootamma2_semibold, weight = FontWeight.SemiBold),


    )
val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = balooFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = balooFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = balooFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)