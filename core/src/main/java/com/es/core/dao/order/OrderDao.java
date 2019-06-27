package com.es.core.dao.order;

import com.es.core.dao.CrudDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderDao extends CrudDao<Order> {
    long saveOrder(Order order);

    List<OrderItem> findOrderItemsByOrderId(Long orderId);

    void setOrderStatusById(Long id, OrderStatus status);

    OrderStatus getOrderStatusById(Long id);
}
