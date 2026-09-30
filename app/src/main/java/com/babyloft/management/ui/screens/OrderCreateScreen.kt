package com.babyloft.management.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.ui.theme.BackgroundCream
import com.babyloft.management.ui.theme.PinkPrimary
import com.babyloft.management.ui.theme.TextDark
import com.babyloft.management.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCreateScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    var step by remember { mutableStateOf(1) }
    var customerName by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var productType by remember { mutableStateOf("Mameluco Bordado") }
    var fabric by remember { mutableStateOf("Algodón 100%") }
    var color by remember { mutableStateOf("Rosa Pastel") }
    var size by remember { mutableStateOf("0-3 Meses") }
    var textToEmbroider by remember { mutableStateOf("") }
    var totalValue by remember { mutableStateOf("48000") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (step <= 3) "Nuevo Pedido (Paso $step de 3)" else "¡Pedido creado!", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { if (step > 1) step-- else onBack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
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
            when (step) {
                1 -> {
                    Text("1. Datos de Contacto del Comprador", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Nombre Completo") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = instagram,
                        onValueChange = { instagram = it },
                        label = { Text("Usuario de Instagram (@usuario)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Número de Teléfono / WhatsApp") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { step = 2 },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Siguiente: Materiales", fontSize = 16.sp)
                    }
                }
                2 -> {
                    Text("2. Selección de Materiales y Diseño", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                    OutlinedTextField(
                        value = productType,
                        onValueChange = { productType = it },
                        label = { Text("Tipo de Producto") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = fabric,
                        onValueChange = { fabric = it },
                        label = { Text("Tela (ej. Algodón)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Color") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = size,
                        onValueChange = { size = it },
                        label = { Text("Talla") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { step = 3 },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Siguiente: Bordado y Total", fontSize = 16.sp)
                    }
                }
                3 -> {
                    Text("3. Bordado y Valor Total", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                    OutlinedTextField(
                        value = textToEmbroider,
                        onValueChange = { textToEmbroider = it },
                        label = { Text("Texto a Bordar") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = totalValue,
                        onValueChange = { totalValue = it },
                        label = { Text("Valor Total (COP)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { step = 4 },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Confirmar y Crear Pedido", fontSize = 16.sp)
                    }
                }
                4 -> {
                    // Pantalla 7: Success feedback screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Éxito",
                            tint = PinkPrimary,
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text = "¡Pedido creado!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(text = "El pedido para ${if (customerName.isNotBlank()) customerName else "María García"} ha sido registrado exitosamente con estado inicial: ✨ Nuevo.", textAlign = TextAlign.Center, color = TextGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onFinish,
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Ver todos los pedidos", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true, backgroundColor = 0xFFFAFAFA, widthDp = 360, heightDp = 640)
@Composable
fun OrderCreatePreview() {
    com.babyloft.management.ui.theme.BabyLoftManagementTheme {
        OrderCreateScreen(onBack = {}, onFinish = {})
    }
}
