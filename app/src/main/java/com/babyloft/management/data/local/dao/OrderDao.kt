package com.babyloft.management.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.local.entity.OrderEntity
import com.babyloft.management.data.local.entity.OrderItemEntity
import com.babyloft.management.data.local.entity.OrderWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>): List<Long>

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Transaction
    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    suspend fun getOrderWithDetailsById(id: Long): OrderWithDetails?

    @Transaction
    @Query("SELECT * FROM orders WHERE orderNumber = :orderNumber LIMIT 1")
    suspend fun getOrderWithDetailsByNumber(orderNumber: String): OrderWithDetails?

    @Transaction
    @Query("SELECT * FROM orders ORDER BY createdAt DESC")
    fun getAllOrdersWithDetails(): Flow<List<OrderWithDetails>>

    @Transaction
    @Query("SELECT * FROM orders WHERE status = :status ORDER BY createdAt DESC")
    fun getOrdersByStatus(status: OrderStatus): Flow<List<OrderWithDetails>>

    @Transaction
    @Query("SELECT * FROM orders ORDER BY createdAt DESC LIMIT :limit")
    fun getRecentOrders(limit: Int = 5): Flow<List<OrderWithDetails>>

    @Query("SELECT COUNT(*) FROM orders WHERE status = :status")
    fun getCountByStatus(status: OrderStatus): Flow<Int>

    @Query("SELECT COUNT(*) FROM orders")
    suspend fun getTotalOrderCount(): Int

    @Query("SELECT MAX(id) FROM orders")
    suspend fun getMaxOrderId(): Long?

    @Query("UPDATE orders SET status = :newStatus WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: Long, newStatus: OrderStatus)
}
