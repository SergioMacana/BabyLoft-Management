package com.babyloft.management.data.repository

import com.babyloft.management.data.ProductItem
import com.babyloft.management.data.local.BabyLoftDatabase
import com.babyloft.management.data.local.entity.CategoryEntity
import com.babyloft.management.data.local.toDomain
import com.babyloft.management.data.local.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(database: BabyLoftDatabase) {

    private val productDao = database.productDao()
    private val categoryDao = database.categoryDao()

    val activeProducts: Flow<List<ProductItem>> = productDao.getActiveProductsWithCategory()
        .map { list -> list.map { it.toDomain() } }

    val categories: Flow<List<String>> = categoryDao.getAll()
        .map { list -> list.map { it.name } }

    suspend fun addProduct(productItem: ProductItem): Long {
        val categoryId = if (productItem.category.isNotBlank()) {
            val existing = categoryDao.getByName(productItem.category)
            existing?.id ?: categoryDao.insert(CategoryEntity(name = productItem.category))
        } else null

        val validCatId = if (categoryId != null && categoryId > 0) categoryId else null
        val entity = productItem.toEntity(categoryId = validCatId)
        return productDao.insert(entity)
    }

    suspend fun updateProduct(productItem: ProductItem) {
        val categoryId = if (productItem.category.isNotBlank()) {
            val existing = categoryDao.getByName(productItem.category)
            existing?.id ?: categoryDao.insert(CategoryEntity(name = productItem.category))
        } else null

        val validCatId = if (categoryId != null && categoryId > 0) categoryId else null
        val entity = productItem.toEntity(categoryId = validCatId)
        productDao.update(entity)
    }

    suspend fun deleteProduct(productItem: ProductItem) {
        val entity = productItem.toEntity()
        productDao.delete(entity)
    }
}
