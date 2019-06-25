package com.es.core.dao.order;

import com.es.core.dao.CrudDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderDao extends CrudDao<Order> {
    long saveOrder(Order order);

    List<OrderItem> findOrderItemsByOrderId(Long orderId);
}
