package com.babyloft.management.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.babyloft.management.data.ProductItem
import com.babyloft.management.data.SampleData
import com.babyloft.management.ui.components.BabyLoftBottomBar
import com.babyloft.management.ui.components.BottomBarTab
import com.babyloft.management.ui.theme.*
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCatalogScreen(
    onBack: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToCreate: () -> Unit
) {
    val products = SampleData.products
    var editingProduct by remember { mutableStateOf<ProductItem?>(null) }
    var isCreatingNew by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo y Productos 🐰", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    Button(
                        onClick = { isCreatingNew = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Producto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundCream)
            )
        },
        bottomBar = {
            BabyLoftBottomBar(
                currentTab = BottomBarTab.CATALOGO,
                onNavigateToDashboard = onNavigateToDashboard,
                onNavigateToOrders = onNavigateToOrders,
                onNavigateToCatalog = {},
                onNavigateToCreate = onNavigateToCreate,
            )
        },
        containerColor = BackgroundCream
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Productos configurados (${products.size})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
            }

            items(products, key = { it.id }) { product ->
                CatalogItemCard(
                    product = product,
                    onEdit = { editingProduct = product },
                    onDelete = { products.remove(product) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    // Dialog for Editing Existing Product
    editingProduct?.let { product ->
        ProductEditDialog(
            product = product,
            isNew = false,
            onDismiss = { editingProduct = null },
            onSave = { updatedProduct ->
                val index = products.indexOfFirst { it.id == updatedProduct.id }
                if (index != -1) {
                    products[index] = updatedProduct
                }
                editingProduct = null
            }
        )
    }

    // Dialog for Creating New Product
    if (isCreatingNew) {
        ProductEditDialog(
            product = ProductItem(
                id = "P_${UUID.randomUUID().toString().take(6)}",
                name = "",
                description = "",
                price = 0.0,
                imageEmoji = "👚",
                category = "General"
            ),
            isNew = true,
            onDismiss = { isCreatingNew = false },
            onSave = { newProduct ->
                products.add(0, newProduct)
                isCreatingNew = false
            }
        )
    }
}

@Composable
fun CatalogItemCard(
    product: ProductItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Image or Emoji Avatar
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SoftPinkCard,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (!product.imageUri.isNullOrBlank()) {
                            AsyncImage(
                                model = product.imageUri,
                                contentDescription = product.name,
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(text = product.imageEmoji, fontSize = 30.sp)
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextDark
                        )
                        Surface(
                            color = SoftMintCard,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = product.category,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontSize = 11.sp,
                                color = Color(0xFF00796B),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (product.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = product.description,
                            fontSize = 12.sp,
                            color = TextGray,
                            maxLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$${String.format("%,.0f", product.price)} COP",
                        fontWeight = FontWeight.Bold,
                        color = PinkPrimary,
                        fontSize = 15.sp
                    )
                }
            }

            Divider(color = Color(0xFFF0F0F0), modifier = Modifier.padding(vertical = 10.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Eliminar", fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = SoftPinkCard, contentColor = PinkPrimary),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProductEditDialog(
    product: ProductItem,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onSave: (ProductItem) -> Unit
) {
    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var priceInput by remember { mutableStateOf(if (product.price > 0) product.price.toInt().toString() else "") }
    var category by remember { mutableStateOf(product.category) }
    var imageEmoji by remember { mutableStateOf(product.imageEmoji) }
    var selectedImageUri by remember { mutableStateOf(product.imageUri) }

    // System Image Picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri.toString()
        }
    }

    val emojis = listOf("👚", "🛁", "👕", "🍼", "🧸", "🛏️", "🍩", "👗", "🎒", "🧵", "🎀")

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    val priceVal = priceInput.toDoubleOrNull() ?: 0.0
                    onSave(
                        product.copy(
                            name = name,
                            description = description,
                            price = priceVal,
                            category = category.ifBlank { "General" },
                            imageEmoji = imageEmoji,
                            imageUri = selectedImageUri
                        )
                    )
                },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = TextGray)
            }
        },
        title = {
            Text(
                text = if (isNew) "Agregar Nuevo Producto" else "Editar Producto",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextDark
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre del producto *") },
                    placeholder = { Text("Ej: Toalla con Capucha") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Category
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoría") },
                    placeholder = { Text("Ej: Cobijas, Baño, Pijamas") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Price
                OutlinedTextField(
                    value = priceInput,
                    onValueChange = { priceInput = it },
                    label = { Text("Precio (COP) *") },
                    placeholder = { Text("Ej: 85000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción del producto") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Emoji Picker
                Text("Icono / Emoji representativo", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextGray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    emojis.take(6).forEach { emoji ->
                        Surface(
                            onClick = { imageEmoji = emoji },
                            shape = CircleShape,
                            color = if (imageEmoji == emoji) SoftPinkCard else Color(0xFFF5F5F5),
                            border = if (imageEmoji == emoji) BorderStroke(1.5.dp, PinkPrimary) else null,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(emoji, fontSize = 18.sp)
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 4.dp))

                // Reference Image Selection
                Text("Imagen de referencia", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)

                if (!selectedImageUri.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F0F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Imagen de referencia",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, PinkPrimary)
                ) {
                    Icon(Icons.Default.Image, contentDescription = null, tint = PinkPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (selectedImageUri.isNullOrBlank()) "📷 Elegir imagen de galería" else "🔄 Cambiar imagen",
                        color = PinkPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (!selectedImageUri.isNullOrBlank()) {
                    TextButton(
                        onClick = { selectedImageUri = null },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Quitar imagen de referencia", color = Color.Red, fontSize = 12.sp)
                    }
                }
            }
        }
    )
}
