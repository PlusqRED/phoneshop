package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    long saveOrder(Order order);

    Optional<Order> findById(Long orderId);

    List<OrderItem> findOrderItemsByOrderId(Long orderId);
}
