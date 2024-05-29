package com.superman.movieticket.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable

fun ButtonLoading (
    content: @Composable RowScope.() -> Unit,
    isLoading: Boolean,
    modifier: Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(onClick = { /*TODO*/ }, enabled = !isLoading, colors = colors) {
        FlowRow (verticalArrangement = Arrangement.Center, horizontalArrangement = Arrangement.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(30.dp).then(modifier),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                content()
            }
        }
    }
}