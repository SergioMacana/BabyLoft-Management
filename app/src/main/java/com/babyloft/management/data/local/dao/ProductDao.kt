package com.babyloft.management.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.babyloft.management.data.local.entity.ProductEntity
import com.babyloft.management.data.local.entity.ProductWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity): Long

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): ProductEntity?

    @Query("SELECT * FROM products WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveProducts(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM products WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveProductsWithCategory(): Flow<List<ProductWithCategory>>

    @Query("SELECT * FROM products WHERE categoryId = :categoryId AND isActive = 1")
    fun getByCategory(categoryId: Long): Flow<List<ProductEntity>>
}
