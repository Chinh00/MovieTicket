package com.superman.movieticket.ui.order.ticket.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScreenShape() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            val width = size.width
            val height = size.height

            // Gradient brush cho bóng mờ dần
            val gradientBrush = Brush.verticalGradient(
                colors = listOf(
                    Color.Gray.copy(alpha = 0.5f),  // Màu bóng bắt đầu với độ mờ
                    Color.Transparent               // Kết thúc trong suốt
                ),
                startY = 0f,
                endY = height
            )

            val shadowPath = Path().apply {
                moveTo(0f, height * 0.8f)  // Bắt đầu từ điểm bên trái dưới
                cubicTo(
                    width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                    width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                    width, height * 0.8f   // Điểm cuối cùng bên phải
                )
            }
            val shadowPath1 = Path().apply {
                moveTo(0f, height * 1f)  // Bắt đầu từ điểm bên trái dưới
                cubicTo(
                    width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                    width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                    width, height * 1f   // Điểm cuối cùng bên phải
                )
            }

            // Vẽ đường cong đổ bóng với gradient
            drawPath(
                path = shadowPath,
                brush = gradientBrush,
                style = Stroke(width = 10.dp.toPx())
            )
            drawPath(
                path = shadowPath,
                brush = gradientBrush,
                style = Stroke(width = 10.dp.toPx())
            )

            // Vẽ đường cong chính
            val mainPath = Path().apply {
                moveTo(0f, height * 0.6f)  // Bắt đầu từ điểm bên trái dưới
                cubicTo(
                    width * 0.25f, 0f,     // Điểm điều khiển thứ nhất
                    width * 0.75f, 0f,     // Điểm điều khiển thứ hai
                    width, height * 0.6f   // Điểm cuối cùng bên phải
                )
            }

            drawPath(
                path = mainPath,
                color = Color.Gray,
                style = Stroke(width = 4.dp.toPx())
            )
        }
    }
}