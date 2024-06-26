package com.superman.movieticket.ui.order.ticket.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.superman.movieticket.domain.entities.Seat

@Composable
fun SeatComp(
    isEnable: Boolean = false,
    isSelected: Boolean = false,
    seat: Seat,
    onClick: (String) -> Unit = { _ -> },
) {
    val seatColor = when {
        !seat.isPlaced -> Color.Gray
        isSelected -> Color(0xFFAF6D0C)
        else -> Color.White
    }
    val textColor = when {
        isSelected -> Color.White
        else -> MaterialTheme.colorScheme.onBackground
    }
    Box(modifier = Modifier
        .size(32.dp)
        .border(1.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
        .clip(
            RoundedCornerShape(8.dp)
        )
        .background(seatColor)
        .clickable(enabled = !seat.isPlaced) { onClick(seat.id) }
        .padding(5.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(text = "${seat.rowNumber}${seat.colNumber}", style = MaterialTheme.typography.bodyMedium.copy(color = textColor))
    }
}

