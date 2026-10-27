package com.babyloft.management.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.babyloft.management.data.OrderStatus

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = CustomerEntity::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("customerId"),
        Index(value = ["orderNumber"], unique = true)
    ]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderNumber: String,
    val customerId: Long,
    val status: OrderStatus = OrderStatus.NUEVOS,
    val createdAt: Long = System.currentTimeMillis(),
    val deliveryDate: String = "",
    val total: Double = 0.0,
    val deposit: Double = 0.0,
    val notes: String = "",
    val embroideryFont: String = "Block redondeado"
)
