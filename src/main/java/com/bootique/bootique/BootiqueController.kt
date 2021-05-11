package com.bootique.bootique

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

/**
 * RESTful endpoints are exposed in this component.
 */
@RestController
class BootiqueController(private val productRepository: ProductRepository, private val basketRepository: BasketRepository) {

    @GetMapping("/products")
    fun products(): List<Product> = productRepository.getProducts()

    @GetMapping("/baskets/{id}")
    fun getBasket(@PathVariable("id") id: String): Basket = basketRepository.getBasketById(id)

    @PostMapping(path = ["/baskets/{id}/items"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addToBasket(@PathVariable("id") id: String, @RequestBody orderItem: OrderItem): Basket {
        val productById = productRepository.getProductById(orderItem.productId)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id: ${orderItem.productId} not found.")
        val basket = basketRepository.getBasketById(id)
        basket.addOrderItem(orderItem.copy(price = productById.listPrice))
        return basket
    }
}