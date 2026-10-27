package com.babyloft.management.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.babyloft.management.data.local.entity.OrderStatusHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderStatusHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: OrderStatusHistoryEntity): Long

    @Query("SELECT * FROM order_status_history WHERE orderId = :orderId ORDER BY changedAt ASC")
    fun getHistoryForOrder(orderId: Long): Flow<List<OrderStatusHistoryEntity>>
}
