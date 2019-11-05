package com.bootique.bootique;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Basket contains the order items for a specific user or session.
 */
public class Basket {
    private final List<OrderItem> orderItems;

    public Basket() {
        orderItems = new ArrayList<>();
    }

    public Basket(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    /**
     * Calculated sum of the order item totalPrice.
     *
     * @return BigDecimal.ZERO in case of an empty basket.
     */
    public BigDecimal getTotalPrice() {
        return orderItems.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
