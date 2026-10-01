package com.babyloft.management.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.R
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.SampleData
import com.babyloft.management.ui.components.BabyLoftBottomBar
import com.babyloft.management.ui.components.BottomBarTab
import com.babyloft.management.ui.components.OrderStatusBadge
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
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("🐰", fontSize = 22.sp)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Text("•••", fontSize = 18.sp, color = PinkPrimary, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream)
            )
        },
        bottomBar = {
            BabyLoftBottomBar(
                currentTab = BottomBarTab.INICIO,
                onNavigateToDashboard = {},
                onNavigateToOrders = onNavigateToOrders,
                onNavigateToCreate = onNavigateToCreate,
            )
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
                // Banner Principal
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFFF1BCE6), Color(0xFFBDB4FF))
                            )
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.babyloft_logo),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(36.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                        Column {
                            Text("Panel Administrador", fontSize = 12.sp, color = Color.White.copy(alpha = 0.9f), fontWeight = FontWeight.Medium)
                            Text("Baby Loft 🐰", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("Miércoles, 3 sep 2026", fontSize = 11.sp, color = Color.White.copy(alpha = 0.9f))
                        }
                    }
                }
            }

            item {
                Text("Resumen de pedidos", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Nuevos",
                            count = nuevosCount,
                            emoji = "✨",
                            backgroundColor = Color(0xFFFDE8F0),
                            textColor = Color(0xFFC2185B)
                        )
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "En producción",
                            count = prodCount,
                            emoji = "🧵",
                            backgroundColor = Color(0xFFD7F5F0),
                            textColor = Color(0xFF00796B)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Listos",
                            count = listosCount,
                            emoji = "✅",
                            backgroundColor = Color(0xFFEBE8FC),
                            textColor = Color(0xFF673AB7)
                        )
                        SummaryCard(
                            modifier = Modifier.weight(1f),
                            title = "Enviados",
                            count = enviadosCount,
                            emoji = "📦",
                            backgroundColor = Color(0xFFFFF5CD),
                            textColor = Color(0xFFD96B00)
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
                    Text("Pedidos recientes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = onNavigateToCreate,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
                            shape = CircleShape,
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text("+ Nuevo", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFDE8F0),
                            modifier = Modifier
                                .height(32.dp)
                                .clickable { onNavigateToOrders() }
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                                Text("Ver todos →", color = Color(0xFFC2185B), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            items(orders.take(2)) { order ->
                RecentOrderCard(order = order, onClick = { onNavigateToDetail(order) })
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun SummaryCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int,
    emoji: String,
    backgroundColor: Color,
    textColor: Color
) {
    Card(
        modifier = modifier.height(128.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = emoji, fontSize = 24.sp)
            Text(text = count.toString(), fontSize = 26.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = title, fontSize = 13.sp, color = textColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun RecentOrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = order.id, fontWeight = FontWeight.Bold, color = Color(0xFFC2185B), fontSize = 14.sp)
                OrderStatusBadge(status = order.status)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = order.customerName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "${order.productDescription} · ${order.instagramHandle}", fontSize = 12.sp, color = TextGray)
        }
    }
}