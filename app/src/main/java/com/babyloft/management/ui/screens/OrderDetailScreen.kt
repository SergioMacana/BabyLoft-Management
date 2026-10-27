package com.babyloft.management.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.repository.OrderRepository
import com.babyloft.management.ui.components.OrderStatusBadge
import com.babyloft.management.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    order: Order,
    orderRepository: OrderRepository? = null,
    onBack: () -> Unit
) {
    var currentStatus by remember { mutableStateOf(order.status) }
    var showChangeDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(order.id, fontSize = 13.sp, color = PinkPrimary, fontWeight = FontWeight.Bold)
                        Text(order.customerName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado actual card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Estado actual", fontSize = 12.sp, color = TextGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        OrderStatusBadge(status = currentStatus)
                    }
                    Button(
                        onClick = { showChangeDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text("Cambiar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Progreso del pedido timeline
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Progreso del pedido", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TimelineStep(title = "Nuevo", isActive = currentStatus == OrderStatus.NUEVOS, isCompleted = currentStatus != OrderStatus.NUEVOS)
                        TimelineLine()
                        TimelineStep(title = "En Producción", isActive = currentStatus == OrderStatus.EN_PRODUCCION, isCompleted = currentStatus == OrderStatus.LISTOS || currentStatus == OrderStatus.ENVIADOS)
                        TimelineLine()
                        TimelineStep(title = "Listo", isActive = currentStatus == OrderStatus.LISTOS, isCompleted = currentStatus == OrderStatus.ENVIADOS)
                        TimelineLine()
                        TimelineStep(title = "Enviado", isActive = currentStatus == OrderStatus.ENVIADOS, isCompleted = false)
                    }
                }
            }

            // Cliente card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("👤", fontSize = 16.sp)
                        Text("Cliente", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                    }
                    Divider(color = Color(0xFFF0F0F0))
                    DetailRow(label = "Instagram", value = order.instagramHandle)
                    DetailRow(label = "Teléfono", value = order.phone)
                    DetailRow(label = "Día / Fecha entrega", value = order.deliveryDate.ifBlank { "No especificada" })
                    DetailRow(label = "Fecha registro", value = order.orderDate)
                }
            }

            // Productos del kit card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("👗", fontSize = 16.sp)
                        Text("Productos del Kit (${order.items.size})", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                    }
                    Divider(color = Color(0xFFF0F0F0))

                    order.items.forEachIndexed { idx, item ->
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "${idx + 1}. ${item.productName}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = PinkPrimary
                            )
                            DetailRow(label = "Tela", value = item.fabric)
                            DetailRow(label = "Color", value = item.color)
                            DetailRow(label = "Talla", value = item.size)
                            if (item.textToEmbroider.isNotBlank()) {
                                DetailRow(label = "Bordado", value = "\"${item.textToEmbroider}\"")
                            }
                            if (idx < order.items.size - 1) {
                                Divider(color = Color(0xFFF8F8F8), modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
            }

            // Personalización & Notas card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("✍️", fontSize = 16.sp)
                        Text("Personalización general", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PinkPrimary)
                    }
                    Divider(color = Color(0xFFF0F0F0))
                    DetailRow(label = "Fuente de bordado", value = order.font)
                    DetailRow(label = "Notas / Observaciones", value = order.notes.ifBlank { "Ninguna" })
                }
            }

            // Resumen financiero card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SoftPinkCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("💰", fontSize = 16.sp)
                        Text("Resumen financiero", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PinkPrimary)
                    }
                    Divider(color = Color(0xFFE0E0E0))
                    DetailRow(label = "Valor total del kit", value = "$${String.format("%,.0f", order.total)}")
                    DetailRow(label = "Abono realizado", value = "$${String.format("%,.0f", order.deposit)}")
                    DetailRow(label = "Pendiente por pagar", value = "$${String.format("%,.0f", order.balance)}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showChangeDialog) {
        AlertDialog(
            onDismissRequest = { showChangeDialog = false },
            title = { Text("Cambiar estado del pedido") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OrderStatus.entries.forEach { status ->
                        OutlinedButton(
                            onClick = {
                                currentStatus = status
                                showChangeDialog = false
                                if (orderRepository != null) {
                                    scope.launch {
                                        orderRepository.updateOrderStatus(order.id, status)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (currentStatus == status) SoftPinkCard else SurfaceWhite
                            )
                        ) {
                            Text(status.label, color = if (currentStatus == status) PinkPrimary else TextDark)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showChangeDialog = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextGray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp, color = TextDark)
    }
}

@Composable
fun TimelineStep(title: String, isActive: Boolean, isCompleted: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(50.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = if (isActive || isCompleted) PinkPrimary else Color(0xFFE0E0E0),
            modifier = Modifier.size(28.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isActive) {
                    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.White))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            fontSize = 10.sp,
            color = if (isActive) PinkPrimary else TextGray,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1
        )
    }
}

@Composable
fun TimelineLine() {
    Box(
        modifier = Modifier
            .width(24.dp)
            .height(2.dp)
            .background(Color(0xFFE0E0E0))
    )
}
