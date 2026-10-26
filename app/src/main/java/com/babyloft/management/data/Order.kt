package com.babyloft.management.data

import androidx.compose.runtime.mutableStateListOf
import java.util.UUID

enum class OrderStatus(val label: String) {
    NUEVOS("Nuevos"),
    EN_PRODUCCION("En producción"),
    LISTOS("Listos"),
    ENVIADOS("Enviados")
}

data class OrderItem(
    val id: String = UUID.randomUUID().toString(),
    val productName: String = "Cobija",
    val fabric: String = "Algodón pima",
    val color: String = "Amarillo",
    val size: String = "9-12 meses",
    val textToEmbroider: String = "",
    val price: Double = 0.0
)

data class Order(
    val id: String,
    val customerName: String,
    val phone: String = "",
    val instagramHandle: String = "",
    val items: List<OrderItem> = emptyList(),
    val status: OrderStatus = OrderStatus.NUEVOS,
    val total: Double = 0.0,
    val deposit: Double = 0.0,
    val deliveryDate: String = "",
    val notes: String = "",
    val font: String = "Block redondeado",
    val orderDate: String = "1 de septiembre de 2026"
) {
    // Helper properties for display and summary
    val productDescription: String
        get() = if (items.isEmpty()) "Sin productos" else items.joinToString(", ") { it.productName }

    val fabric: String
        get() = items.firstOrNull()?.fabric ?: "N/A"

    val color: String
        get() = items.firstOrNull()?.color ?: "N/A"

    val size: String
        get() = items.firstOrNull()?.size ?: "N/A"

    val textToEmbroider: String
        get() = items.firstOrNull { it.textToEmbroider.isNotBlank() }?.textToEmbroider
            ?: items.firstOrNull()?.textToEmbroider ?: ""

    val balance: Double
        get() = (total - deposit).coerceAtLeast(0.0)
}

data class ProductItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageEmoji: String,
    val category: String
)

object SampleData {
    val orders = mutableStateListOf(
        Order(
            id = "#BL-001",
            customerName = "María García",
            phone = "+57 300 456 7890",
            instagramHandle = "@mariag_bebe",
            status = OrderStatus.EN_PRODUCCION,
            total = 252000.0,
            deposit = 60000.0,
            deliveryDate = "Lunes 28",
            notes = "Kit cojineria completo",
            font = "Block redondeado",
            orderDate = "2 de septiembre de 2026",
            items = listOf(
                OrderItem(productName = "Cobija", fabric = "Algodón pima", color = "Azul", size = "Única", textToEmbroider = "Martín", price = 85000.0),
                OrderItem(productName = "Toalla con Capucha", fabric = "Toalla felpa", color = "Azul", size = "Única", textToEmbroider = "Martín", price = 55000.0),
                OrderItem(productName = "Dona", fabric = "Suaventina", color = "Azul", size = "Única", textToEmbroider = "", price = 42000.0),
                OrderItem(productName = "Cojín E", fabric = "Suaventina", color = "Azul", size = "Única", textToEmbroider = "Martín", price = 70000.0)
            )
        ),
        Order(
            id = "#BL-002",
            customerName = "Valentina Ruiz",
            phone = "+57 311 987 6543",
            instagramHandle = "@vale_mama",
            status = OrderStatus.NUEVOS,
            total = 381000.0,
            deposit = 200000.0,
            deliveryDate = "Lunes 28",
            notes = "Kit completo con ropa baby",
            font = "Script cursivo",
            orderDate = "1 de septiembre de 2026",
            items = listOf(
                OrderItem(productName = "Cobija", fabric = "Interlock suave", color = "Rosa", size = "Única", textToEmbroider = "Amalet", price = 95000.0),
                OrderItem(productName = "Toalla con Capucha", fabric = "Toalla felpa", color = "Rosa", size = "Única", textToEmbroider = "Amalet", price = 55000.0),
                OrderItem(productName = "Dona", fabric = "Suaventina", color = "Rosa", size = "Única", textToEmbroider = "", price = 42000.0),
                OrderItem(productName = "Cojín E", fabric = "Suaventina", color = "Rosa", size = "Única", textToEmbroider = "Amalet", price = 69000.0),
                OrderItem(productName = "Salida de baño", fabric = "Toalla", color = "Rosa", size = "0-3 meses", textToEmbroider = "Amalet", price = 120000.0)
            )
        ),
        Order(
            id = "#BL-003",
            customerName = "Camila Torres",
            phone = "+57 320 123 4567",
            instagramHandle = "@cami_tots",
            status = OrderStatus.LISTOS,
            total = 145000.0,
            deposit = 145000.0,
            deliveryDate = "Miércoles 30",
            notes = "Caja de regalo incluida",
            font = "Serif clásico",
            orderDate = "31 de agosto de 2026",
            items = listOf(
                OrderItem(productName = "Set Pijama 3 piezas", fabric = "Tela nido de abeja", color = "Lavanda", size = "0-3 Meses", textToEmbroider = "Luna", price = 145000.0)
            )
        ),
        Order(
            id = "#BL-004",
            customerName = "Andrea López",
            phone = "+57 315 987 1234",
            instagramHandle = "@andreita_mami",
            status = OrderStatus.ENVIADOS,
            total = 95000.0,
            deposit = 95000.0,
            deliveryDate = "Jueves 31",
            notes = "Envío express a domicilio",
            font = "Monoline",
            orderDate = "28 de agosto de 2026",
            items = listOf(
                OrderItem(productName = "Cobijita Térmica", fabric = "Polar corderito", color = "Melocotón", size = "Única", textToEmbroider = "Emiliano", price = 95000.0)
            )
        )
    )

    val products = listOf(
        ProductItem("P1", "Cobija", "Cobija suave 100% algodón pima con bordado personalizado.", 85000.0, "👚", "Cobijas"),
        ProductItem("P2", "Toalla con Capucha", "Toalla de felpa ultra absorbente con capucha bordada.", 55000.0, "🛁", "Baño"),
        ProductItem("P3", "Set Pijama 2 piezas", "Conjunto de pantalón y blusa en interlock suave y antialérgico.", 120000.0, "👕", "Pijamas"),
        ProductItem("P4", "Set Pijama 3 piezas", "Incluye pijama, gorrito y manoplas a juego.", 145000.0, "🍼", "Pijamas"),
        ProductItem("P5", "Cobijita Térmica", "Cobija suave de polar corderito ideal para paseo y cuna.", 95000.0, "🧸", "Accesorios"),
        ProductItem("P6", "Cojín E", "Cojín ergonómico decorativo para cuna.", 69000.0, "🛏️", "Cojines"),
        ProductItem("P7", "Dona", "Dona de lactancia o apoyo.", 42000.0, "🍩", "Accesorios")
    )
}
