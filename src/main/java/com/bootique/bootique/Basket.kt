package com.bootique.bootique

import java.math.BigDecimal

/**
 * Basket contains the order items for a specific user or session.
 */
class Basket(val items: MutableList<OrderItem> = mutableListOf()) {

    /**
     * Calculated sum of the order item totalPrice.
     * @return BigDecimal.ZERO in case of an empty basket.
     */
    val totalPrice: BigDecimal
        get() = items.sumBy { it.totalPrice }

    fun addOrderItem(orderItem: OrderItem) = items.add(orderItem)
}
