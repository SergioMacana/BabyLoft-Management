package com.babyloft.management.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.SampleData
import com.babyloft.management.ui.components.BabyLoftBottomBar
import com.babyloft.management.ui.components.BottomBarTab
import com.babyloft.management.ui.components.OrderStatusBadge
import com.babyloft.management.ui.theme.BackgroundCream
import com.babyloft.management.ui.theme.SurfaceWhite
import com.babyloft.management.ui.theme.TextDark
import com.babyloft.management.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OrderListScreen(
    onBack: () -> Unit,
    onNavigateToDetail: (Order) -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToCreate: () -> Unit,
) {
    var selectedFilter by remember { mutableStateOf<OrderStatus?>(null) }
    val orders = SampleData.orders
    val filteredOrders = if (selectedFilter == null) orders else orders.filter { it.status == selectedFilter }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("🐰", fontSize = 22.sp)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Text("•••", fontSize = 18.sp, color = Color(0xFFC2185B), fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream),
            )
        },
        bottomBar = {
            BabyLoftBottomBar(
                currentTab = BottomBarTab.PEDIDOS,
                onNavigateToDashboard = onNavigateToDashboard,
                onNavigateToOrders = {},
                onNavigateToCreate = onNavigateToCreate,
            )
        },
        containerColor = BackgroundCream,
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Todos los pedidos",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    item {
                        OrderFilterChip(
                            selected = selectedFilter == null,
                            emoji = "🐰",
                            label = "Todos",
                            onClick = { selectedFilter = null },
                        )
                    }
                    items(OrderStatus.entries.toTypedArray()) { status ->
                        val (emoji, label) = when (status) {
                            OrderStatus.NUEVOS -> Pair("✨", "Nuevo")
                            OrderStatus.EN_PRODUCCION -> Pair("🧵", "En Producción")
                            OrderStatus.LISTOS -> Pair("✅", "Listo")
                            OrderStatus.ENVIADOS -> Pair("📦", "Enviado")
                        }
                        OrderFilterChip(
                            selected = selectedFilter == status,
                            emoji = emoji,
                            label = label,
                            onClick = { selectedFilter = status },
                        )
                    }
                }
            }

            items(filteredOrders) { order ->
                DetailedOrderCard(order = order, onClick = { onNavigateToDetail(order) })
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun OrderFilterChip(
    selected: Boolean,
    emoji: String?,
    label: String,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (selected) Color(0xFFFDE8F0) else Color(0xFFF5F5F5),
        border = if (selected) BorderStroke(1.5.dp, Color(0xFFC2185B)) else null,
        modifier = Modifier.height(36.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (emoji != null) {
                Text(emoji, fontSize = 13.sp)
            }
            Text(
                text = label,
                fontSize = 13.sp,
                color = if (selected) Color(0xFFC2185B) else Color(0xFF5D4037),
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailedOrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            // Header Row: Order ID + Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = order.id,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC2185B),
                    fontSize = 14.sp,
                )
                OrderStatusBadge(status = order.status)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Second Row: Customer Info + Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text(
                        text = order.customerName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextDark,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = order.instagramHandle,
                        fontSize = 13.sp,
                        color = TextGray,
                    )
                }

                Text(
                    text = "$${String.format("%,.0f", order.total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF5C6BC0),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Tag Chips Row matching Screenshot
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                // Fabric Tag
                Surface(
                    color = Color(0xFFD7F5F0),
                    shape = CircleShape,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("🥦", fontSize = 12.sp)
                        Text(
                            text = order.fabric,
                            fontSize = 12.sp,
                            color = Color(0xFF00796B),
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                // Color Tag
                Surface(
                    color = Color(0xFFFDE8F0),
                    shape = CircleShape,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEC407A)),
                        )
                        Text(
                            text = order.color,
                            fontSize = 12.sp,
                            color = Color(0xFFC2185B),
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                // Embroidery Text Tag
                Surface(
                    color = Color(0xFFFFF5CD),
                    shape = CircleShape,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text("🍰", fontSize = 12.sp)
                        Text(
                            text = "\"${order.textToEmbroider}\"",
                            fontSize = 12.sp,
                            color = Color(0xFFD96B00),
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
}
