package com.babyloft.management.data.local

import androidx.room.TypeConverter
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.local.entity.AttributeType

class Converters {
    @TypeConverter
    fun fromOrderStatus(status: OrderStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toOrderStatus(value: String?): OrderStatus? {
        return value?.let {
            try {
                OrderStatus.valueOf(it)
            } catch (e: Exception) {
                OrderStatus.NUEVOS
            }
        }
    }

    @TypeConverter
    fun fromAttributeType(type: AttributeType?): String? {
        return type?.name
    }

    @TypeConverter
    fun toAttributeType(value: String?): AttributeType? {
        return value?.let {
            try {
                AttributeType.valueOf(it)
            } catch (e: Exception) {
                AttributeType.FABRIC
            }
        }
    }
}
