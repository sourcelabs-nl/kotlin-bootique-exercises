package com.bootique.bootique;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Basket contains the order items for a specific user or session.
 */
public class Basket {
    private final List<OrderItem> items;

    public Basket() {
        items = new ArrayList<>();
    }

    public Basket(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addOrderItem(OrderItem orderItem) {
        items.add(orderItem);
    }

    /**
     * Calculated sum of the order item totalPrice.
     *
     * @return BigDecimal.ZERO in case of an empty basket.
     */
    public BigDecimal getTotalPrice() {
        return items.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
