package com.bootique.bootique

import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.concurrent.ConcurrentHashMap

/**
 * Dummy implementation of a Product persistent store, keeps the products in memory.
 *
 * We use a map here just to have something else than a List ;-)
 */
@Repository
class ProductRepository {

    fun getProducts() = products.values.toList()

    fun getProductById(productId: String): Product? = products[productId]

    companion object {
        private val products = ConcurrentHashMap(listOf(
                Product("1", "iPhone X", "Apple", BigDecimal("989.99")),
                Product("2", "Galaxy S8", "Samsung", BigDecimal("699.99")),
                Product("3", "3310", "Nokia", BigDecimal("19.95")),
                Product("4", "Kermit", "KPN", BigDecimal("6.95"))
        ).associateBy { it.id })
    }
}
