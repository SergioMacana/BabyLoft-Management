package com.babyloft.management.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.babyloft.management.data.local.entity.AttributeEntity
import com.babyloft.management.data.local.entity.AttributeType
import com.babyloft.management.data.repository.AttributeRepository
import com.babyloft.management.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMaterialsScreen(
    attributeRepository: AttributeRepository? = null,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Telas, 1: Colores, 2: Tallas

    val fabrics by (attributeRepository?.fabrics?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf<List<AttributeEntity>>(emptyList()) })
    val colors by (attributeRepository?.colors?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf<List<AttributeEntity>>(emptyList()) })
    val sizes by (attributeRepository?.sizes?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf<List<AttributeEntity>>(emptyList()) })

    var editingAttribute by remember { mutableStateOf<AttributeEntity?>(null) }
    var isAddingNew by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val currentType = when (selectedTab) {
        0 -> AttributeType.FABRIC
        1 -> AttributeType.COLOR
        else -> AttributeType.SIZE
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Telas, Colores y Tallas 🧵", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark) },
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
                actions = {
                    Button(
                        onClick = { isAddingNew = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (selectedTab) {
                                0 -> "+ Tela"
                                1 -> "+ Color"
                                else -> "+ Talla"
                            },
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Tabs Selector
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = SurfaceWhite,
                contentColor = PinkPrimary,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("🧵 Telas (${fabrics.size})", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("🎨 Colores (${colors.size})", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("📏 Tallas (${sizes.size})", fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) }
                )
            }

            // List
            val currentList = when (selectedTab) {
                0 -> fabrics
                1 -> colors
                else -> sizes
            }

            if (currentList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(if (selectedTab == 0) "🧵" else if (selectedTab == 1) "🎨" else "📏", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No hay opciones configuradas", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("Presiona el botón superior para agregar una nueva opción", fontSize = 13.sp, color = TextGray)
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentList, key = { it.id }) { item ->
                        AttributeItemCard(
                            attribute = item,
                            onEdit = { editingAttribute = item },
                            onDelete = {
                                if (attributeRepository != null) {
                                    scope.launch {
                                        attributeRepository.deleteAttribute(item)
                                    }
                                }
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }

    // Edit Dialog
    editingAttribute?.let { attribute ->
        AttributeEditDialog(
            attribute = attribute,
            isNew = false,
            onDismiss = { editingAttribute = null },
            onSave = { updated ->
                if (attributeRepository != null) {
                    scope.launch {
                        attributeRepository.updateAttribute(updated)
                    }
                }
                editingAttribute = null
            }
        )
    }

    // Add New Dialog
    if (isAddingNew) {
        AttributeEditDialog(
            attribute = AttributeEntity(
                type = currentType,
                name = "",
                hexColor = if (currentType == AttributeType.COLOR) "#FFB6C1" else null
            ),
            isNew = true,
            onDismiss = { isAddingNew = false },
            onSave = { newAttr ->
                if (attributeRepository != null) {
                    scope.launch {
                        attributeRepository.addAttribute(newAttr.type, newAttr.name, newAttr.hexColor)
                    }
                }
                isAddingNew = false
            }
        )
    }
}

@Composable
fun AttributeItemCard(
    attribute: AttributeEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (attribute.type == AttributeType.COLOR) {
                    val parsedColor = parseHexColor(attribute.hexColor)
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(parsedColor)
                            .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                    )
                } else {
                    Surface(
                        shape = CircleShape,
                        color = SoftPinkCard,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(if (attribute.type == AttributeType.FABRIC) "🧵" else "📏", fontSize = 16.sp)
                        }
                    }
                }

                Text(
                    text = attribute.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = PinkPrimary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun AttributeEditDialog(
    attribute: AttributeEntity,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onSave: (AttributeEntity) -> Unit
) {
    var name by remember { mutableStateOf(attribute.name) }
    var hexColor by remember { mutableStateOf(attribute.hexColor ?: "#FFB6C1") }

    val titleText = when (attribute.type) {
        AttributeType.FABRIC -> if (isNew) "Agregar Nueva Tela" else "Editar Tela"
        AttributeType.COLOR -> if (isNew) "Agregar Nuevo Color" else "Editar Color"
        AttributeType.SIZE -> if (isNew) "Agregar Nueva Talla" else "Editar Talla"
    }

    val presetColors = listOf(
        Pair("Rosa", "#FFB6C1"),
        Pair("Lavanda", "#C5CAE9"),
        Pair("Menta", "#80E2B7"),
        Pair("Azul", "#B3E5FC"),
        Pair("Melocotón", "#FFE0B2"),
        Pair("Amarillo", "#FFF59D"),
        Pair("Lila", "#E1BEE7"),
        Pair("Blanco", "#FFFFFF"),
        Pair("Coral", "#FFAB91"),
        Pair("Verde", "#A5D6A7"),
        Pair("Rojo", "#EF5350"),
        Pair("Negro", "#212121")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onSave(attribute.copy(name = name, hexColor = if (attribute.type == AttributeType.COLOR) hexColor else null))
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
            Text(titleText, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre / Descripción *") },
                    placeholder = {
                        Text(
                            when (attribute.type) {
                                AttributeType.FABRIC -> "Ej: Algodón pima"
                                AttributeType.COLOR -> "Ej: Rosa pastel"
                                AttributeType.SIZE -> "Ej: 9-12 meses"
                            }
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                if (attribute.type == AttributeType.COLOR) {
                    Text("Muestra de color", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        val colorPreview = parseHexColor(hexColor)
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(colorPreview)
                                .border(1.5.dp, PinkPrimary, CircleShape)
                        )
                        OutlinedTextField(
                            value = hexColor,
                            onValueChange = { hexColor = it },
                            label = { Text("Código HEX (#RRGGBB)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Text("Colores sugeridos", fontSize = 12.sp, color = TextGray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        presetColors.take(6).forEach { (colorName, hex) ->
                            val colorValue = parseHexColor(hex)
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(colorValue)
                                    .border(
                                        width = if (hexColor.equals(hex, ignoreCase = true)) 2.dp else 1.dp,
                                        color = if (hexColor.equals(hex, ignoreCase = true)) PinkPrimary else Color(0xFFE0E0E0),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        hexColor = hex
                                        if (name.isBlank()) name = colorName
                                    }
                            )
                        }
                    }
                }
            }
        }
    )
}

fun parseHexColor(hex: String?): Color {
    if (hex.isNullOrBlank()) return Color(0xFFFFB6C1)
    return try {
        val cleanHex = hex.removePrefix("#")
        val colorInt = android.graphics.Color.parseColor("#$cleanHex")
        Color(colorInt)
    } catch (e: Exception) {
        Color(0xFFFFB6C1)
    }
}
