package com.babyloft.management.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.babyloft.management.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(customer: CustomerEntity): Long

    @Update
    suspend fun update(customer: CustomerEntity)

    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): CustomerEntity?

    @Query("SELECT * FROM customers WHERE phone = :phone LIMIT 1")
    suspend fun getByPhone(phone: String): CustomerEntity?

    @Query("SELECT * FROM customers ORDER BY fullName ASC")
    fun getAll(): Flow<List<CustomerEntity>>
}
