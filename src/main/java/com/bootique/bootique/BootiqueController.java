package com.bootique.bootique;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RESTful endpoints are exposed in this component.
 */
@RestController
public class BootiqueController {

    private final ProductRepository productRepository;
    private final BasketRepository basketRepository;

    public BootiqueController(ProductRepository productRepository, BasketRepository basketRepository) {
        this.productRepository = productRepository;
        this.basketRepository = basketRepository;
    }

    @GetMapping("/products")
    public List<Product> products() {
        return productRepository.getProducts();
    }

    @GetMapping("/baskets/{id}")
    public Basket getBasket(@PathVariable("id") String id) {
        return basketRepository.getBasketById(id);
    }

    @PostMapping(path = "/baskets/{id}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Basket addToBasket(@PathVariable("id") String id, @RequestBody OrderItem orderItem) {
        Product productById = productRepository.getProductById(orderItem.getProductId());
        if (productById == null) {
            throw new RuntimeException("Product not found!");
        }
        Basket basket = basketRepository.getBasketById(id);
        basket.addOrderItem(new OrderItem(orderItem.getProductId(), orderItem.getQuantity(), productById.getListPrice()));
        return basket;
    }
}
