package com.bootique.bootique

import java.math.BigDecimal

/**
 * Represents the product, quantity and price of an item in the Basket.
 */
data class OrderItem(
    val productId: String,
    val quantity: Int,
    val price: BigDecimal = BigDecimal.ZERO
) {
    /**
     * Calculates the totalPrice of this item: price * quantity
     *
     * @return BigDecimal.ZERO if no price is defined
     */
    val totalPrice: BigDecimal = price * quantity
}

operator fun BigDecimal.times(quantity: Int) = this.times(BigDecimal(quantity))