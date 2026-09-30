package com.babyloft.management.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.SampleData
import com.babyloft.management.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToOrders: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Order) -> Unit,
    onNavigateToCatalog: () -> Unit
) {
    val orders = SampleData.orders
    val nuevosCount = orders.count { it.status == OrderStatus.NUEVOS }
    val prodCount = orders.count { it.status == OrderStatus.EN_PRODUCCION }
    val listosCount = orders.count { it.status == OrderStatus.LISTOS }
    val enviadosCount = orders.count { it.status == OrderStatus.ENVIADOS }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFCE4EC),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🐰", fontSize = 18.sp)
                            }
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "Menu",
                            tint = PinkPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream)
            )
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                NavigationBar(
                    containerColor = SurfaceWhite,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio", color = PinkPrimary, fontWeight = FontWeight.Bold) },
                        selected = true,
                        onClick = {}
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ListAlt, contentDescription = "Pedidos") },
                        label = { Text("Pedidos") },
                        selected = false,
                        onClick = onNavigateToOrders
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Storefront, contentDescription = "Catálogo") },
                        label = { Text("Catálogo") },
                        selected = false,
                        onClick = onNavigateToCatalog
                    )
                }

                // Center Floating Plus Button matching the screenshot
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-20).dp)
                ) {
                    FloatingActionButton(
                        onClick = onNavigateToCreate,
                        containerColor = PinkPrimary,
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        containerColor = BackgroundCream
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Admin Banner Card with gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFEEF4),
                                    Color(0xFFE1BEE7),
                                    Color(0xFFD1C4E9)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("🐰", fontSize = 28.sp)
                            }
                        }
                        Column {
                            Text(
                                text = "Panel Administrador",
                                fontSize = 12.sp,
                                color = TextGray,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Baby Loft 🐰",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Miércoles, 3 sep 2026",
                                fontSize = 12.sp,
                                color = TextGray
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Resumen de pedidos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            item {
                // 2x2 Grid of Summary Cards
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Nuevos",
                            count = nuevosCount,
                            icon = "✨",
                            backgroundColor = SoftPinkCard,
                            textColor = Color(0xFFD81B60)
                        )
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "En producción",
                            count = prodCount,
                            icon = "🧵",
                            backgroundColor = SoftMintCard,
                            textColor = Color(0xFF00796B)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Listos",
                            count = listosCount,
                            icon = "✅",
                            backgroundColor = SoftLavenderCard,
                            textColor = Color(0xFF512DA8)
                        )
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Enviados",
                            count = enviadosCount,
                            icon = "📦",
                            backgroundColor = SoftYellowCard,
                            textColor = Color(0xFFF57F17)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pedidos recientes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = onNavigateToCreate,
                            colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("+ Nuevo", fontSize = 12.sp, color = Color.White)
                        }
                        TextButton(onClick = onNavigateToOrders) {
                            Text("Ver todos →", color = PinkPrimary, fontSize = 14.sp)
                        }
                    }
                }
            }

            items(orders.take(3)) { order ->
                RecentOrderCard(order = order, onClick = { onNavigateToDetail(order) })
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    icon: String,
    backgroundColor: Color,
    textColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = count.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RecentOrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.id,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary,
                    fontSize = 15.sp
                )
                OrderStatusBadge(status = order.status)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = order.customerName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${order.productDescription} · ${order.instagramHandle}",
                fontSize = 13.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$${String.format("%,.0f", order.total)} COP",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PinkPrimary
            )
        }
    }
}

@Composable
fun OrderStatusBadge(status: OrderStatus) {
    val (bgColor, textColor, label) = when (status) {
        OrderStatus.NUEVOS -> Triple(Color(0xFFFCE4EC), Color(0xFFD81B60), "✨ Nuevo")
        OrderStatus.EN_PRODUCCION -> Triple(Color(0xFFE0F2F1), Color(0xFF00796B), "🧵 En Producción")
        OrderStatus.LISTOS -> Triple(Color(0xFFEDE7F6), Color(0xFF512DA8), "✅ Listo")
        OrderStatus.ENVIADOS -> Triple(Color(0xFFFFFDE7), Color(0xFFF57F17), "📦 Enviado")
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
