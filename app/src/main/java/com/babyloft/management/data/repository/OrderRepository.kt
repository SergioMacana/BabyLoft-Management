package com.babyloft.management.data.repository

import androidx.room.withTransaction
import com.babyloft.management.data.Order
import com.babyloft.management.data.OrderItem
import com.babyloft.management.data.OrderStatus
import com.babyloft.management.data.local.BabyLoftDatabase
import com.babyloft.management.data.local.entity.CustomerEntity
import com.babyloft.management.data.local.entity.OrderEntity
import com.babyloft.management.data.local.entity.OrderItemEntity
import com.babyloft.management.data.local.entity.OrderStatusHistoryEntity
import com.babyloft.management.data.local.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepository(private val database: BabyLoftDatabase) {

    private val orderDao = database.orderDao()
    private val customerDao = database.customerDao()
    private val statusHistoryDao = database.orderStatusHistoryDao()

    val allOrders: Flow<List<Order>> = orderDao.getAllOrdersWithDetails()
        .map { list -> list.map { it.toDomain() } }

    val recentOrders: Flow<List<Order>> = orderDao.getRecentOrders(limit = 5)
        .map { list -> list.map { it.toDomain() } }

    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>> {
        return orderDao.getOrdersByStatus(status)
            .map { list -> list.map { it.toDomain() } }
    }

    fun getCountByStatus(status: OrderStatus): Flow<Int> {
        return orderDao.getCountByStatus(status)
    }

    suspend fun getOrderById(orderNumber: String): Order? {
        return orderDao.getOrderWithDetailsByNumber(orderNumber)?.toDomain()
    }

    suspend fun generateOrderNumber(): String {
        val maxId = orderDao.getMaxOrderId() ?: 0L
        val nextNumber = maxId + 1
        return "BL-${nextNumber.toString().padStart(3, '0')}"
    }

    suspend fun createOrder(
        customerName: String,
        phone: String,
        instagramHandle: String,
        items: List<OrderItem>,
        total: Double,
        deposit: Double,
        deliveryDate: String,
        notes: String,
        embroideryFont: String
    ): String {
        return database.withTransaction {
            // 1. Find or create customer
            val existingCustomer = if (phone.isNotBlank()) {
                customerDao.getByPhone(phone)
            } else null

            val customerId = if (existingCustomer != null) {
                // Update customer info if changed
                if (existingCustomer.fullName != customerName || existingCustomer.instagramHandle != instagramHandle) {
                    val updated = existingCustomer.copy(
                        fullName = customerName,
                        instagramHandle = instagramHandle.ifBlank { existingCustomer.instagramHandle }
                    )
                    customerDao.update(updated)
                }
                existingCustomer.id
            } else {
                customerDao.insert(
                    CustomerEntity(
                        fullName = customerName,
                        phone = phone.ifBlank { "Sin teléfono" },
                        instagramHandle = instagramHandle
                    )
                )
            }

            // 2. Generate unique order number
            val orderNumber = generateOrderNumber()

            // 3. Insert Order
            val orderEntity = OrderEntity(
                orderNumber = orderNumber,
                customerId = customerId,
                status = OrderStatus.NUEVOS,
                createdAt = System.currentTimeMillis(),
                deliveryDate = deliveryDate,
                total = total,
                deposit = deposit,
                notes = notes,
                embroideryFont = embroideryFont
            )
            val orderId = orderDao.insertOrder(orderEntity)

            // 4. Insert Order Items
            val itemEntities = items.map { item ->
                OrderItemEntity(
                    orderId = orderId,
                    productName = item.productName,
                    fabric = item.fabric,
                    color = item.color,
                    size = item.size,
                    textToEmbroider = item.textToEmbroider,
                    unitPrice = item.price,
                    quantity = 1
                )
            }
            orderDao.insertOrderItems(itemEntities)

            // 5. Insert initial status history
            statusHistoryDao.insert(
                OrderStatusHistoryEntity(
                    orderId = orderId,
                    previousStatus = null,
                    newStatus = OrderStatus.NUEVOS,
                    changedAt = System.currentTimeMillis(),
                    notes = "Pedido creado con estado inicial NUEVOS"
                )
            )

            orderNumber
        }
    }

    suspend fun updateOrderStatus(
        orderNumber: String,
        newStatus: OrderStatus,
        notes: String? = null
    ) {
        database.withTransaction {
            val orderWithDetails = orderDao.getOrderWithDetailsByNumber(orderNumber) ?: return@withTransaction
            val previousStatus = orderWithDetails.order.status

            if (previousStatus != newStatus) {
                orderDao.updateOrderStatus(orderWithDetails.order.id, newStatus)
                statusHistoryDao.insert(
                    OrderStatusHistoryEntity(
                        orderId = orderWithDetails.order.id,
                        previousStatus = previousStatus,
                        newStatus = newStatus,
                        changedAt = System.currentTimeMillis(),
                        notes = notes ?: "Cambio de estado a ${newStatus.label}"
                    )
                )
            }
        }
    }
}
