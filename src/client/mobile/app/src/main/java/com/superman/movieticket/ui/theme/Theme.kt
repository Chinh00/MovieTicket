package com.superman.movieticket.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration

private val DarkColorPalette = darkColorScheme(
    primary = CustomColor1,
    secondary = CustomColor2,
    background = Color.Black,
    surface = Color.DarkGray,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onPrimaryContainer = Color(0xFFC07C66),
            onPrimary = Color(0xFFAD2B00)

)

private val LightColorPalette = lightColorScheme(
    primary = CustomColor1,
    secondary = CustomColor2,
    background = Color.White,
    surface = Color.LightGray,
    onPrimary = Color(0xFFC07C66)
    ,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    primaryContainer = Color(0xFF792105),
    onPrimaryContainer = Color(0xFFAD2B00)

)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }



    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography,
        shapes = CustomShapes,
        content = content
    )
}