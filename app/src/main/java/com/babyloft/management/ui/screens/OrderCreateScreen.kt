package com.babyloft.management.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color Palette
private val PrimaryPink = Color(0xFFD81B60)
private val DarkPinkText = Color(0xFF8A1C58)
private val LightPinkCard = Color(0xFFFDEBF2)
private val LightTealCard = Color(0xFFE8F8F5)
private val DarkTealText = Color(0xFF00695C)
private val LightPurpleCard = Color(0xFFF3E5F5)
private val DarkPurpleText = Color(0xFF6A1B9A)
private val SubtitleGray = Color(0xFF886B7B)
private val BorderColor = Color(0xFFE8D0DC)
private val ActiveStepColor = Color(0xFFEC407A)
private val InactiveStepColor = Color(0xFFFCE4EC)
private val GradientStart = Color(0xFFF48FB1)
private val GradientEnd = Color(0xFFB388FF)
private val ScreenBackground = Color(0xFFFDF7FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCreateScreen(
    onNavigateBack: () -> Unit = {},
    onOrderCreated: () -> Unit = {}
) {
    var currentStep by remember { mutableIntStateOf(1) }

    // Step 1 State: Client Info
    var fullName by remember { mutableStateOf("maria florez") }
    var instagram by remember { mutableStateOf("@ maria_flores") }
    var phone by remember { mutableStateOf("3117394628") }

    // Step 2 State: Product & Materials
    var selectedProduct by remember { mutableStateOf("Set Pijama 2 piezas") }
    var selectedFabric by remember { mutableStateOf("Algodón pima") }
    var selectedColorId by remember { mutableStateOf("amarillo") }
    var selectedSize by remember { mutableStateOf("9-12 meses") }

    // Step 3 State: Customization & Price
    var textToEmbroider by remember { mutableStateOf("SOFIA") }
    var selectedFont by remember { mutableStateOf("Block redondeado") }
    var specialNotes by remember { mutableStateOf("estrellas y corazones") }
    var totalPrice by remember { mutableStateOf("$ 85000") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ScreenBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Header Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        if (currentStep > 1) currentStep-- else onNavigateBack()
                    },
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFFF8E7EE), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = DarkPinkText
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Nuevo pedido",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkPinkText
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "🐰", fontSize = 18.sp)
                    }
                    Text(
                        text = "Paso $currentStep de 3",
                        fontSize = 13.sp,
                        color = SubtitleGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar (3 steps)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 1..3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (i <= currentStep) ActiveStepColor else InactiveStepColor)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Scrollable Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (currentStep) {
                    1 -> Step1ClientInfo(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        instagram = instagram,
                        onInstagramChange = { instagram = it },
                        phone = phone,
                        onPhoneChange = { phone = it },
                        onNext = { currentStep = 2 }
                    )
                    2 -> Step2ProductsInfo(
                        selectedProduct = selectedProduct,
                        onProductChange = { selectedProduct = it },
                        selectedFabric = selectedFabric,
                        onFabricChange = { selectedFabric = it },
                        selectedColorId = selectedColorId,
                        onColorChange = { selectedColorId = it },
                        selectedSize = selectedSize,
                        onSizeChange = { selectedSize = it },
                        onNext = { currentStep = 3 },
                        onBack = { currentStep = 1 }
                    )
                    3 -> Step3CustomizationInfo(
                        textToEmbroider = textToEmbroider,
                        onTextToEmbroiderChange = { textToEmbroider = it },
                        selectedFont = selectedFont,
                        onFontChange = { selectedFont = it },
                        specialNotes = specialNotes,
                        onSpecialNotesChange = { specialNotes = it },
                        totalPrice = totalPrice,
                        onTotalPriceChange = { totalPrice = it },
                        onConfirm = onOrderCreated,
                        onBack = { currentStep = 2 }
                    )
                }
            }
        }
    }
}

@Composable
private fun Step1ClientInfo(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    instagram: String,
    onInstagramChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightPinkCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = DarkPinkText,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Información del cliente",
                        fontWeight = FontWeight.Bold,
                        color = DarkPinkText,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Datos de contacto de quien realiza el pedido",
                        fontSize = 12.sp,
                        color = SubtitleGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Input Fields
        CustomInputField(
            label = "Nombre completo *",
            value = fullName,
            onValueChange = onFullNameChange,
            placeholder = "maria florez"
        )

        CustomInputField(
            label = "Instagram *",
            value = instagram,
            onValueChange = onInstagramChange,
            placeholder = "@ maria_flores"
        )

        CustomInputField(
            label = "Teléfono / WhatsApp *",
            value = phone,
            onValueChange = onPhoneChange,
            placeholder = "3117394628",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Continuar →",
            onClick = onNext,
            enabled = fullName.isNotBlank() && phone.isNotBlank()
        )
    }
}

@Composable
private fun Step2ProductsInfo(
    selectedProduct: String,
    onProductChange: (String) -> Unit,
    selectedFabric: String,
    onFabricChange: (String) -> Unit,
    selectedColorId: String,
    onColorChange: (String) -> Unit,
    selectedSize: String,
    onSizeChange: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val productsList = listOf(
        "Set Pijama 2 piezas",
        "Mameluco Bordado",
        "Set Pijama 3 piezas",
        "Toalla con Capucha",
        "Cobijita Térmica",
        "Vestido Tejido"
    )

    val fabricsList = listOf(
        "Algodón pima",
        "Interlock suave",
        "Tela nido de abeja",
        "Polar corderito",
        "Lana antialérgica"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightTealCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👗", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Producto y materiales",
                        fontWeight = FontWeight.Bold,
                        color = DarkTealText,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Tela, color y talla del pedido",
                        fontSize = 12.sp,
                        color = DarkTealText.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Product Dropdown
        CustomDropdownField(
            label = "Producto *",
            selectedValue = selectedProduct,
            options = productsList,
            onValueChange = onProductChange
        )

        // Fabric Dropdown
        CustomDropdownField(
            label = "Tipo de tela *",
            selectedValue = selectedFabric,
            options = fabricsList,
            onValueChange = onFabricChange
        )

        // Color Selector Card
        ColorSelector(
            selectedColorId = selectedColorId,
            onColorSelected = onColorChange
        )

        // Size Selector
        SizeSelector(
            selectedSize = selectedSize,
            onSizeSelected = onSizeChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Navigation Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedSecondaryButton(
                text = "← Atrás",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            GradientButton(
                text = "Continuar →",
                onClick = onNext,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private data class ColorItem(
    val id: String,
    val name: String,
    val color: Color,
    val statusText: String
)

private val colorItems = listOf(
    ColorItem("rosa", "Rosa", Color(0xFFFFB6C1), "Rosa suave"),
    ColorItem("lavanda", "Lavanda", Color(0xFFC5CAE9), "Lavanda suave"),
    ColorItem("menta", "Menta", Color(0xFF80E2B7), "Menta suave"),
    ColorItem("azul", "Azul", Color(0xFFB3E5FC), "Azul suave"),
    ColorItem("melocoton", "Melocotón", Color(0xFFFFE0B2), "Melocotón suave"),
    ColorItem("amarillo", "Amarillo", Color(0xFFFFF59D), "Amarillo suave"),
    ColorItem("lila", "Lila", Color(0xFFE1BEE7), "Lila suave"),
    ColorItem("blanco", "Blanco", Color(0xFFFFFFFF), "Blanco puro"),
    ColorItem("coral", "Coral", Color(0xFFFFAB91), "Coral suave"),
    ColorItem("verde", "Verde", Color(0xFFA5D6A7), "Verde suave")
)

@Composable
private fun ColorSelector(
    selectedColorId: String,
    onColorSelected: (String) -> Unit
) {
    val selectedItem = colorItems.find { it.id == selectedColorId } ?: colorItems[5]

    Column {
        Text(
            text = "Color *",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkPinkText,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderColor)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Row 1 (5 colors)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    colorItems.take(5).forEach { item ->
                        ColorCircleItem(
                            item = item,
                            isSelected = item.id == selectedColorId,
                            onClick = { onColorSelected(item.id) }
                        )
                    }
                }

                // Row 2 (5 colors)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    colorItems.drop(5).take(5).forEach { item ->
                        ColorCircleItem(
                            item = item,
                            isSelected = item.id == selectedColorId,
                            onClick = { onColorSelected(item.id) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Selected Status
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = PrimaryPink,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${selectedItem.statusText} seleccionado",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkPinkText
            )
        }
    }
}

@Composable
private fun ColorCircleItem(
    item: ColorItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(item.color)
                .border(
                    width = if (isSelected) 2.5.dp else 1.dp,
                    color = if (isSelected) PrimaryPink else Color(0xFFE0E0E0),
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.name,
            fontSize = 10.sp,
            color = SubtitleGray
        )
    }
}

@Composable
private fun SizeSelector(
    selectedSize: String,
    onSizeSelected: (String) -> Unit
) {
    val sizes = listOf(
        listOf("Recién nacido", "0-3 meses", "3-6 meses"),
        listOf("6-9 meses", "9-12 meses", "12 meses"),
        listOf("18 meses", "24 meses", "Única")
    )

    Column {
        Text(
            text = "Talla *",
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkPinkText,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            sizes.forEach { rowSizes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowSizes.forEach { size ->
                        val isSelected = size == selectedSize
                        Surface(
                            onClick = { onSizeSelected(size) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White,
                            border = BorderStroke(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) PrimaryPink else BorderColor
                            )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Text(
                                    text = size,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) DarkPinkText else SubtitleGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Step3CustomizationInfo(
    textToEmbroider: String,
    onTextToEmbroiderChange: (String) -> Unit,
    selectedFont: String,
    onFontChange: (String) -> Unit,
    specialNotes: String,
    onSpecialNotesChange: (String) -> Unit,
    totalPrice: String,
    onTotalPriceChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val fontOptions = listOf(
        "Script cursivo",
        "Block redondeado",
        "Sans moderno",
        "Serif clásico",
        "Monoline"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightPurpleCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🧵", fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Personalización y precio",
                        fontWeight = FontWeight.Bold,
                        color = DarkPurpleText,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Nombre a bordar, fuente y detalles especiales",
                        fontSize = 12.sp,
                        color = DarkPurpleText.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Text to embroider
        CustomInputField(
            label = "Nombre / texto a bordar",
            value = textToEmbroider,
            onValueChange = onTextToEmbroiderChange,
            placeholder = "SOFIA"
        )

        // Font options list
        Column {
            Text(
                text = "Fuente del bordado",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkPinkText,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                fontOptions.forEach { font ->
                    val isSelected = font == selectedFont
                    Surface(
                        onClick = { onFontChange(font) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = Color.White,
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) Color(0xFF8E24AA) else BorderColor
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = if (isSelected) 5.dp else 1.5.dp,
                                        color = if (isSelected) Color(0xFF8E24AA) else BorderColor,
                                        shape = CircleShape
                                    )
                                    .background(Color.White)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = font,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) DarkPurpleText else DarkPinkText
                            )
                        }
                    }
                }
            }
        }

        // Special notes
        Column {
            Text(
                text = "Notas especiales",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = DarkPinkText,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = specialNotes,
                onValueChange = onSpecialNotesChange,
                placeholder = { Text("Detalles especiales...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(Color.White, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPink,
                    unfocusedBorderColor = BorderColor,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        // Total price
        CustomInputField(
            label = "Valor total (COP) *",
            value = totalPrice,
            onValueChange = onTotalPriceChange,
            placeholder = "$ 85000",
            keyboardType = KeyboardType.Number
        )

        // Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LightPinkCard),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderColor)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "📋", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Resumen del pedido",
                    fontWeight = FontWeight.Bold,
                    color = DarkPinkText,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedSecondaryButton(
                text = "← Atrás",
                onClick = onBack,
                modifier = Modifier.weight(1f)
            )
            GradientButton(
                text = "Crear Pedido 🚀",
                onClick = onConfirm,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkPinkText,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryPink,
                unfocusedBorderColor = BorderColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomDropdownField(
    label: String,
    selectedValue: String,
    options: List<String>,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkPinkText,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = DarkPinkText
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .background(Color.White, RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryPink,
                    unfocusedBorderColor = BorderColor,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, color = DarkPinkText) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.LightGray
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun OutlinedSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(1.5.dp, PrimaryPink),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = PrimaryPink
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryPink
        )
    }
}
