package com.superman.movieticket.ui.components

import android.R.style
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun CustomButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier,color:Color) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
        containerColor = color
        )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}