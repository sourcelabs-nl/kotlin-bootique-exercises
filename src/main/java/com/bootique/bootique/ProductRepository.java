package com.bootique.bootique;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Dummy implementation of a Product persistent store, keeps the products in memory.
 *
 * We use a map here just to have something else than a List ;-)
 */
@Repository
public class ProductRepository {

    private static final Map<String, Product> products = Map.of(
        "1", new Product("1", "iPhone XX", "Apple", new BigDecimal("3989.99")),
        "2", new Product("2", "Galaxy S25", "Samsung", new BigDecimal("2699.99")),
        "3", new Product("3", "3310", "Nokia", new BigDecimal("19.95")),
        "4", new Product("4", "Kermit", "KPN", new BigDecimal("6.95"))
    );

    /**
     * @return an unmodifiable list containing products
     */
    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    /**
     * @return null if not found
     */
    public Product getProductById(String productId) {
        return products.get(productId);
    }
}
