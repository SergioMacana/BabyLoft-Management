package com.babyloft.management.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithDetails(
    @Embedded
    val order: OrderEntity,

    @Relation(
        parentColumn = "customerId",
        entityColumn = "id"
    )
    val customer: CustomerEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val history: List<OrderStatusHistoryEntity>
)
