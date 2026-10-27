package com.babyloft.management.data.repository

import com.babyloft.management.data.local.BabyLoftDatabase
import com.babyloft.management.data.local.entity.AttributeEntity
import com.babyloft.management.data.local.entity.AttributeType
import kotlinx.coroutines.flow.Flow

class AttributeRepository(database: BabyLoftDatabase) {

    private val attributeDao = database.attributeDao()

    val fabrics: Flow<List<AttributeEntity>> = attributeDao.getAttributesByType(AttributeType.FABRIC)
    val colors: Flow<List<AttributeEntity>> = attributeDao.getAttributesByType(AttributeType.COLOR)
    val sizes: Flow<List<AttributeEntity>> = attributeDao.getAttributesByType(AttributeType.SIZE)

    suspend fun addAttribute(type: AttributeType, name: String, hexColor: String? = null): Long {
        return attributeDao.insert(
            AttributeEntity(
                type = type,
                name = name.trim(),
                hexColor = hexColor?.trim()
            )
        )
    }

    suspend fun updateAttribute(attribute: AttributeEntity) {
        attributeDao.update(attribute)
    }

    suspend fun deleteAttribute(attribute: AttributeEntity) {
        attributeDao.delete(attribute)
    }
}
