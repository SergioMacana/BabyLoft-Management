package com.babyloft.management.data

enum class OrderStatus(val label: String) {
    NUEVOS("Nuevos"),
    EN_PRODUCCION("En producción"),
    LISTOS("Listos"),
    ENVIADOS("Enviados")
}

data class Order(
    val id: String,
    val customerName: String,
    val productDescription: String,
    val instagramHandle: String,
    val status: OrderStatus,
    val total: Double = 85000.0,
    val fabric: String = "Algodón pima",
    val color: String = "Rosa palo",
    val size: String = "0-3 Meses",
    val textToEmbroider: String = "Sofía",
    val font: String = "Cursiva",
    val notes: String = "Añadir estrellita después del nombre",
    val phone: String = "+57 311 987 6543",
    val orderDate: String = "1 de septiembre de 2026"
)

data class ProductItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageEmoji: String,
    val category: String
)

object SampleData {
    val orders = listOf(
        Order(
            id = "#BL-001",
            customerName = "María García",
            productDescription = "Mameluco Bordado",
            instagramHandle = "@mariag_bebe",
            status = OrderStatus.EN_PRODUCCION,
            total = 85000.0,
            fabric = "Algodón pima",
            color = "Rosa palo",
            size = "3-6 Meses",
            textToEmbroider = "Sofía",
            font = "Cursiva",
            notes = "Detalle de encaje en mangas",
            phone = "+57 300 456 7890",
            orderDate = "2 de septiembre de 2026"
        ),
        Order(
            id = "#BL-002",
            customerName = "Valentina Ruiz",
            productDescription = "Set Pijama 2 piezas",
            instagramHandle = "@vale_mamá",
            status = OrderStatus.NUEVOS,
            total = 120000.0,
            fabric = "Interlock suave",
            color = "Menta",
            size = "6-9 meses",
            textToEmbroider = "Mateo ⭐",
            font = "Block redondeado",
            notes = "Añadir estrellita después del nombre",
            phone = "+57 311 987 6543",
            orderDate = "1 de septiembre de 2026"
        ),
        Order(
            id = "#BL-003",
            customerName = "Camila Torres",
            productDescription = "Set Pijama 3 piezas",
            instagramHandle = "@cami_tots",
            status = OrderStatus.LISTOS,
            total = 145000.0,
            fabric = "Tela nido de abeja",
            color = "Lavanda",
            size = "0-3 Meses",
            textToEmbroider = "Luna",
            font = "Elegante",
            notes = "Caja de regalo incluida",
            phone = "+57 320 123 4567",
            orderDate = "31 de agosto de 2026"
        ),
        Order(
            id = "#BL-004",
            customerName = "Andrea López",
            productDescription = "Cobijita Térmica",
            instagramHandle = "@andreita_mami",
            status = OrderStatus.ENVIADOS,
            total = 95000.0,
            fabric = "Polar corderito",
            color = "Beige",
            size = "Única",
            textToEmbroider = "Emiliano",
            font = "Moderno",
            notes = "Envío express a domicilio",
            phone = "+57 315 987 1234",
            orderDate = "28 de agosto de 2026"
        )
    )

    val products = listOf(
        ProductItem("P1", "Mameluco Bordado", "Mameluco 100% algodón pima con bordado personalizado del nombre.", 85000.0, "👚", "Ropa"),
        ProductItem("P2", "Set Pijama 2 piezas", "Conjunto de pantalón y blusa en interlock suave y antialérgico.", 120000.0, "👕", "Pijamas"),
        ProductItem("P3", "Set Pijama 3 piezas", "Incluye pijama, gorrito y manoplas a juego en tela nido de abeja.", 145000.0, "🍼", "Pijamas"),
        ProductItem("P4", "Toalla con Capucha", "Toalla de felpa ultra absorbente con capucha bordada de animalitos.", 55000.0, "🛁", "Baño"),
        ProductItem("P5", "Cobijita Térmica", "Cobija suave de polar corderito ideal para paseo y cuna.", 95000.0, "🧸", "Accesorios"),
        ProductItem("P6", "Zapatitos de Lana", "Patucos tejidos a mano en lana antialérgica suave.", 38000.0, "🧦", "Calzado"),
        ProductItem("P7", "Vestido Tejido", "Hermoso vestido artesanal con detalles de flores bordadas.", 110000.0, "👗", "Ropa")
    )
}
