package com.bootique.bootique

import org.springframework.stereotype.Repository
import java.math.BigDecimal

/**
 * Dummy implementation of a Product persistent store, keeps the products in memory.
 *
 * We use a map here just to have something else than a List ;-)
 */
@Repository
class ProductRepository {
    /**
     * @return an unmodifiable list containing products
     */
    fun getProducts(): List<Product> = products.values.toList()

    /**
     * @return null if not found
     */
    fun getProductById(productId: String): Product? = products[productId]

    companion object {
        private val products = mapOf(
            "1" to Product("1", "iPhone XX", "Apple", BigDecimal("3989.99")),
            "2" to Product("2", "Galaxy S25", "Samsung", BigDecimal("2699.99")),
            "3" to Product("3", "3310", "Nokia", BigDecimal("19.95")),
            "4" to Product("4", "Kermit", "KPN", BigDecimal("6.95"))
        )
    }
}