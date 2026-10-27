package com.babyloft.management.data.local

import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderItem
import com.babyloft.management.data.ProductItem
import com.babyloft.management.data.local.entity.CategoryEntity
import com.babyloft.management.data.local.entity.CustomerEntity
import com.babyloft.management.data.local.entity.OrderEntity
import com.babyloft.management.data.local.entity.OrderItemEntity
import com.babyloft.management.data.local.entity.OrderWithDetails
import com.babyloft.management.data.local.entity.ProductEntity
import com.babyloft.management.data.local.entity.ProductWithCategory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun OrderWithDetails.toDomain(): Order {
    val dateFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    val dateFormatted = try {
        dateFormat.format(Date(order.createdAt))
    } catch (e: Exception) {
        "Hoy"
    }

    return Order(
        id = order.orderNumber,
        customerName = customer.fullName,
        phone = customer.phone,
        instagramHandle = customer.instagramHandle,
        items = items.map { it.toDomain() },
        status = order.status,
        total = order.total,
        deposit = order.deposit,
        deliveryDate = order.deliveryDate,
        notes = order.notes,
        font = order.embroideryFont,
        orderDate = dateFormatted
    )
}

fun OrderItemEntity.toDomain(): OrderItem {
    return OrderItem(
        id = id.toString(),
        productName = productName,
        fabric = fabric,
        color = color,
        size = size,
        textToEmbroider = textToEmbroider,
        price = unitPrice
    )
}

fun ProductEntity.toDomain(categoryName: String = "General"): ProductItem {
    return ProductItem(
        id = id.toString(),
        name = name,
        description = description,
        price = price,
        imageEmoji = imageEmoji,
        category = categoryName,
        imageUri = imageUri
    )
}

fun ProductWithCategory.toDomain(): ProductItem {
    return product.toDomain(categoryName = category?.name ?: "General")
}

fun ProductItem.toEntity(categoryId: Long? = null): ProductEntity {
    val numericId = id.replace(Regex("[^0-9]"), "").toLongOrNull() ?: 0L
    return ProductEntity(
        id = numericId,
        name = name,
        description = description,
        price = price,
        categoryId = categoryId,
        imageEmoji = imageEmoji,
        imageUri = imageUri,
        isActive = true
    )
}
