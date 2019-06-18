package com.es.core.service.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;

import java.math.BigDecimal;

public interface OrderService {
    Order createOrder(Order order, Cart cart);

    void placeOrder(Order order) throws OutOfStockException;

    BigDecimal getDeliveryPrice();
}
