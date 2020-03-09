package com.bootique.bootique

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

        val basket = basketRepository.getBasketById(id).apply {
            val product = productRepository.getProductById(orderItem.productId)
                    ?: throw RuntimeException("Product with productId: ${orderItem.productId} not found!")
            addOrderItem(OrderItem(orderItem.productId, orderItem.quantity, product.listPrice))
        }
        return ServerResponse.ok().body(basket)
    }
}