package com.bootique.bootique

import java.math.BigDecimal

/**
 * Basket contains the order items for a specific user or session.
 */
class Basket(private val items: MutableList<OrderItem> = mutableListOf()) {

    fun getItems(): List<OrderItem> = items.toList()

    fun addOrderItem(orderItem: OrderItem) = items.add(orderItem)

    /**
     * Calculated sum of the order item totalPrice.
     *
     * @return BigDecimal.ZERO in case of an empty basket.
     */
    fun getTotalPrice(): BigDecimal = items.sumOf { it.totalPrice }
}