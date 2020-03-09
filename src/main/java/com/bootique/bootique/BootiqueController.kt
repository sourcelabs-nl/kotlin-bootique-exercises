package com.bootique.bootique

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * RESTful endpoints are exposed in this component.
 */
//@RestController = disabled because we are using the router and handler functions.
class BootiqueController(private val productRepository: ProductRepository, private val basketRepository: BasketRepository) {

    @GetMapping("/products")
    fun products() = productRepository.getProducts()

    @GetMapping("/baskets/{id}")
    fun getBasket(@PathVariable id: String) = basketRepository.getBasketById(id)

    @PostMapping(path = ["/baskets/{id}/items"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addToBasket(@PathVariable id: String, @RequestBody orderItem: OrderItem): Basket {
        return basketRepository.getBasketById(id).apply {
            val product = productRepository.getProductById(orderItem.productId)
                    ?: throw RuntimeException("Product with productId: ${orderItem.productId} not found!")
            addOrderItem(OrderItem(orderItem.productId, orderItem.quantity, product.listPrice))
        }
    }
}