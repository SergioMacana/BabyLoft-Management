package com.babyloft.management.data.local

import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.local.entity.AttributeEntity
import com.babyloft.management.data.local.entity.AttributeType
import com.babyloft.management.data.local.entity.CategoryEntity
import com.babyloft.management.data.local.entity.CustomerEntity
import com.babyloft.management.data.local.entity.OrderEntity
import com.babyloft.management.data.local.entity.OrderItemEntity
import com.babyloft.management.data.local.entity.OrderStatusHistoryEntity
import com.babyloft.management.data.local.entity.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseSeeder {

    suspend fun seedIfEmpty(database: BabyLoftDatabase) = withContext(Dispatchers.IO) {
        val productDao = database.productDao()
        val categoryDao = database.categoryDao()
        val orderDao = database.orderDao()
        val customerDao = database.customerDao()
        val historyDao = database.orderStatusHistoryDao()
        val attributeDao = database.attributeDao()

        // 1. Seed Attributes if empty
        if (attributeDao.count() == 0) {
            val defaultFabrics = listOf("Algodón pima", "Interlock suave", "Tela nido de abeja", "Polar corderito", "Toalla felpa", "Suaventina")
            defaultFabrics.forEachIndexed { index, fabric ->
                attributeDao.insert(AttributeEntity(type = AttributeType.FABRIC, name = fabric, displayOrder = index))
            }

            val defaultColors = listOf(
                Pair("Rosa", "#FFB6C1"),
                Pair("Lavanda", "#C5CAE9"),
                Pair("Menta", "#80E2B7"),
                Pair("Azul", "#B3E5FC"),
                Pair("Melocotón", "#FFE0B2"),
                Pair("Amarillo", "#FFF59D"),
                Pair("Lila", "#E1BEE7"),
                Pair("Blanco", "#FFFFFF"),
                Pair("Coral", "#FFAB91"),
                Pair("Verde", "#A5D6A7")
            )
            defaultColors.forEachIndexed { index, (colorName, hex) ->
                attributeDao.insert(AttributeEntity(type = AttributeType.COLOR, name = colorName, hexColor = hex, displayOrder = index))
            }

            val defaultSizes = listOf("Recién nacido", "0-3 meses", "3-6 meses", "6-9 meses", "9-12 meses", "12 meses", "18 meses", "24 meses", "Única")
            defaultSizes.forEachIndexed { index, size ->
                attributeDao.insert(AttributeEntity(type = AttributeType.SIZE, name = size, displayOrder = index))
            }
        }

        // 2. Seed Products if empty
        if (orderDao.getTotalOrderCount() == 0 && productDao.getById(1) == null) {
            val cobijasCat = categoryDao.insert(CategoryEntity(name = "Cobijas"))
            val banoCat = categoryDao.insert(CategoryEntity(name = "Baño"))
            val pijamasCat = categoryDao.insert(CategoryEntity(name = "Pijamas"))
            val cojinesCat = categoryDao.insert(CategoryEntity(name = "Cojines"))
            val accesoriosCat = categoryDao.insert(CategoryEntity(name = "Accesorios"))

            productDao.insert(ProductEntity(name = "Cobija", description = "Cobija suave 100% algodón pima con bordado personalizado.", price = 85000.0, categoryId = cobijasCat, imageEmoji = "👚"))
            productDao.insert(ProductEntity(name = "Toalla con Capucha", description = "Toalla de felpa ultra absorbente con capucha bordada.", price = 55000.0, categoryId = banoCat, imageEmoji = "🛁"))
            productDao.insert(ProductEntity(name = "Set Pijama 2 piezas", description = "Conjunto de pantalón y blusa en interlock suave.", price = 120000.0, categoryId = pijamasCat, imageEmoji = "👕"))
            productDao.insert(ProductEntity(name = "Set Pijama 3 piezas", description = "Incluye pijama, gorrito y manoplas a juego.", price = 145000.0, categoryId = pijamasCat, imageEmoji = "🍼"))
            productDao.insert(ProductEntity(name = "Cobijita Térmica", description = "Cobija suave de polar corderito ideal para paseo.", price = 95000.0, categoryId = accesoriosCat, imageEmoji = "🧸"))
            productDao.insert(ProductEntity(name = "Cojín E", description = "Cojín ergonómico decorativo para cuna.", price = 69000.0, categoryId = cojinesCat, imageEmoji = "🛏️"))
            productDao.insert(ProductEntity(name = "Dona", description = "Dona de lactancia o apoyo.", price = 42000.0, categoryId = accesoriosCat, imageEmoji = "🍩"))
        }

        // 3. Seed Orders if empty
        if (orderDao.getTotalOrderCount() == 0) {
            val cust1 = customerDao.insert(CustomerEntity(fullName = "María García", phone = "+57 300 456 7890", instagramHandle = "@mariag_bebe"))
            val order1Id = orderDao.insertOrder(
                OrderEntity(
                    orderNumber = "BL-001",
                    customerId = cust1,
                    status = OrderStatus.EN_PRODUCCION,
                    createdAt = System.currentTimeMillis() - 172800000L,
                    deliveryDate = "Lunes 28",
                    total = 252000.0,
                    deposit = 60000.0,
                    notes = "Kit cojineria completo",
                    embroideryFont = "Block redondeado"
                )
            )
            orderDao.insertOrderItems(
                listOf(
                    OrderItemEntity(orderId = order1Id, productName = "Cobija", fabric = "Algodón pima", color = "Azul", size = "Única", textToEmbroider = "Martín", unitPrice = 85000.0),
                    OrderItemEntity(orderId = order1Id, productName = "Toalla con Capucha", fabric = "Toalla felpa", color = "Azul", size = "Única", textToEmbroider = "Martín", unitPrice = 55000.0),
                    OrderItemEntity(orderId = order1Id, productName = "Dona", fabric = "Suaventina", color = "Azul", size = "Única", textToEmbroider = "", unitPrice = 42000.0),
                    OrderItemEntity(orderId = order1Id, productName = "Cojín E", fabric = "Suaventina", color = "Azul", size = "Única", textToEmbroider = "Martín", unitPrice = 70000.0)
                )
            )
            historyDao.insert(OrderStatusHistoryEntity(orderId = order1Id, previousStatus = OrderStatus.NUEVOS, newStatus = OrderStatus.EN_PRODUCCION, notes = "Inició producción"))

            val cust2 = customerDao.insert(CustomerEntity(fullName = "Valentina Ruiz", phone = "+57 311 987 6543", instagramHandle = "@vale_mama"))
            val order2Id = orderDao.insertOrder(
                OrderEntity(
                    orderNumber = "BL-002",
                    customerId = cust2,
                    status = OrderStatus.NUEVOS,
                    createdAt = System.currentTimeMillis() - 86400000L,
                    deliveryDate = "Lunes 28",
                    total = 381000.0,
                    deposit = 200000.0,
                    notes = "Kit completo con ropa baby",
                    embroideryFont = "Script cursivo"
                )
            )
            orderDao.insertOrderItems(
                listOf(
                    OrderItemEntity(orderId = order2Id, productName = "Cobija", fabric = "Interlock suave", color = "Rosa", size = "Única", textToEmbroider = "Amalet", unitPrice = 95000.0),
                    OrderItemEntity(orderId = order2Id, productName = "Toalla con Capucha", fabric = "Toalla felpa", color = "Rosa", size = "Única", textToEmbroider = "Amalet", unitPrice = 55000.0),
                    OrderItemEntity(orderId = order2Id, productName = "Dona", fabric = "Suaventina", color = "Rosa", size = "Única", textToEmbroider = "", unitPrice = 42000.0),
                    OrderItemEntity(orderId = order2Id, productName = "Cojín E", fabric = "Suaventina", color = "Rosa", size = "Única", textToEmbroider = "Amalet", unitPrice = 69000.0),
                    OrderItemEntity(orderId = order2Id, productName = "Salida de baño", fabric = "Toalla", color = "Rosa", size = "0-3 meses", textToEmbroider = "Amalet", unitPrice = 120000.0)
                )
            )
            historyDao.insert(OrderStatusHistoryEntity(orderId = order2Id, previousStatus = null, newStatus = OrderStatus.NUEVOS, notes = "Pedido creado"))
        }
    }
}
