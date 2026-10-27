package com.babyloft.management.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("orderId"),
        Index("productId")
    ]
)
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val orderId: Long,
    val productId: Long? = null,
    val productName: String,
    val fabric: String = "",
    val color: String = "",
    val size: String = "",
    val textToEmbroider: String = "",
    val unitPrice: Double = 0.0,
    val quantity: Int = 1,
    val referenceImageUri: String? = null
)
