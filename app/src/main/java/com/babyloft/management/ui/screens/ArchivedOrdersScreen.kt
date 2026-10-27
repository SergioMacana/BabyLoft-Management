package com.babyloft.management.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.SampleData
import com.babyloft.management.data.repository.OrderRepository
import com.babyloft.management.ui.theme.BackgroundCream
import com.babyloft.management.ui.theme.PinkPrimary
import com.babyloft.management.ui.theme.SoftPinkCard
import com.babyloft.management.ui.theme.TextDark
import com.babyloft.management.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedOrdersScreen(
    orderRepository: OrderRepository? = null,
    onBack: () -> Unit,
    onNavigateToDetail: (Order) -> Unit
) {
    val roomOrders by (orderRepository?.allOrders?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf<List<Order>>(emptyList()) })
    val allOrders = if (orderRepository != null) roomOrders else SampleData.orders
    val archivedOrders = allOrders.filter { it.status == OrderStatus.ENVIADOS }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Pedidos Archivados 📁", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Surface(
                            shape = CircleShape,
                            color = SoftPinkCard,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = PinkPrimary, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream)
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
                Text(
                    text = "Pedidos enviados y completados (${archivedOrders.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextGray
                )
            }

            if (archivedOrders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("📦", fontSize = 40.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No hay pedidos archivados", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                            Text("Los pedidos marcados como 'Enviado' aparecerán aquí", fontSize = 13.sp, color = TextGray)
                        }
                    }
                }
            } else {
                items(archivedOrders) { order ->
                    DetailedOrderCard(order = order, onClick = { onNavigateToDetail(order) })
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
