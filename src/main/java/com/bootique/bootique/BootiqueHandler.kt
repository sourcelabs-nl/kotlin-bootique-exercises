package com.bootique.bootique

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

/**
 * RESTful endpoints are exposed in this component.
 */
class BootiqueHandler(private val productRepository: ProductRepository, private val basketRepository: BasketRepository) {

    fun products(request: ServerRequest) = ServerResponse.ok().body(productRepository.getProducts())

    fun getBasket(request: ServerRequest) = ServerResponse.ok().body(basketRepository.getBasketById(request.pathVariable("id")))

    fun addToBasket(request: ServerRequest): ServerResponse {
        val orderItem = request.body(OrderItem::class.java)
        val id = request.pathVariable("id")
        val productById = productRepository.getProductById(orderItem.productId)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with id: ${orderItem.productId} not found.")
        val basket = basketRepository.getBasketById(id)
        basket.addOrderItem(orderItem.copy(price = productById.listPrice))
        return ServerResponse.ok().body(basket)
    }
}