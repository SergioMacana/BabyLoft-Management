package com.babyloft.management.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun OrderListScreen(
    onBack: () -> Unit,
    onNavigateToDetail: (Order) -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onNavigateToCreate: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf<OrderStatus?>(null) }
    val orders = SampleData.orders
    val filteredOrders = if (selectedFilter == null) orders else orders.filter { it.status == selectedFilter }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todos los pedidos", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
                        label = { Text("Inicio") },
                        selected = false,
                        onClick = onNavigateToDashboard
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ListAlt, contentDescription = "Pedidos") },
                        label = { Text("Pedidos", color = PinkPrimary, fontWeight = FontWeight.Bold) },
                        selected = true,
                        onClick = {}
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Storefront, contentDescription = "Catálogo") },
                        label = { Text("Catálogo") },
                        selected = false,
                        onClick = onNavigateToCatalog
                    )
                }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Filter Chips matching Screenshot 2
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedFilter == null,
                        onClick = { selectedFilter = null },
                        label = { Text("Todos") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PinkPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
                items(OrderStatus.values()) { status ->
                    FilterChip(
                        selected = selectedFilter == status,
                        onClick = { selectedFilter = status },
                        label = { Text(status.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PinkPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredOrders) { order ->
                    DetailedOrderCard(order = order, onClick = { onNavigateToDetail(order) })
                }
                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun DetailedOrderCard(order: Order, onClick: () -> Unit) {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "$${String.format("%,.0f", order.total)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = PinkPrimary
                    )
                    OrderStatusBadge(status = order.status)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = order.customerName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = order.instagramHandle,
                fontSize = 13.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Material tags row matching screenshot 2
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    color = SoftMintCard,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "🍃 ${order.fabric}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        color = Color(0xFF00796B),
                        fontWeight = FontWeight.Medium
                    )
                }
                Surface(
                    color = SoftPinkCard,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "🌸 ${order.color}",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        color = Color(0xFFD81B60),
                        fontWeight = FontWeight.Medium
                    )
                }
                Surface(
                    color = SoftYellowCard,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "👶 \"${order.textToEmbroider}\"",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        color = Color(0xFFF57F17),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
