package com.bootique.bootique

import java.math.BigDecimal

/**
 * Basket contains the order items for a specific user or session.
 */
class Basket(val orderItems: MutableList<OrderItem> = mutableListOf()) {

    val totalPrice: BigDecimal
        get() = orderItems.sumBy { it.totalPrice }

    fun addOrderItem(orderItem: OrderItem) = orderItems.add(orderItem)
}
