package com.babyloft.management.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.babyloft.management.data.OrderStatus

@Entity(
    tableName = "order_status_history",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("orderId")
    ]
)
data class OrderStatusHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val previousStatus: OrderStatus? = null,
    val newStatus: OrderStatus,
    val changedAt: Long = System.currentTimeMillis(),
    val notes: String? = null
)
