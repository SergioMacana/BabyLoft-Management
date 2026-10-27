package com.babyloft.management.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

enum class AttributeType {
    FABRIC,
    COLOR,
    SIZE
}

@Entity(
    tableName = "attributes",
    indices = [
        Index(value = ["type", "name"], unique = true)
    ]
)
data class AttributeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: AttributeType,
    val name: String,
    val hexColor: String? = null,
    val displayOrder: Int = 0
)
