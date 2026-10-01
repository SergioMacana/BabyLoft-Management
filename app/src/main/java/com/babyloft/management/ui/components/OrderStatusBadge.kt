package com.babyloft.management.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.OrderStatus

@Composable
fun OrderStatusBadge(
    status: OrderStatus,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor, emoji) = when (status) {
        OrderStatus.NUEVOS -> Triple(Color(0xFFFDE8F0), Color(0xFFC2185B), "✨")
        OrderStatus.EN_PRODUCCION -> Triple(Color(0xFFD7F5F0), Color(0xFF00796B), "🧵")
        OrderStatus.LISTOS -> Triple(Color(0xFFEBE8FC), Color(0xFF673AB7), "✅")
        OrderStatus.ENVIADOS -> Triple(Color(0xFFFFF5CD), Color(0xFFD96B00), "📦")
    }

    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = CircleShape,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = emoji, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = status.label,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
