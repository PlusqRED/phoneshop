package com.es.core.service.order;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:properties/order.properties")
public class OrderServiceImpl implements OrderService {

    @Value("${delivery.price}")
    private String deliveryPriceProperty;

    @Override
    public Order createOrder(Order order, Cart cart) {
        order.setDeliveryPrice(getDeliveryPrice());
        order.setSubtotal(cart.getOverallPrice());
        order.setTotalPrice(cart.getOverallPrice().add(getDeliveryPrice()));
        order.setOrderItems(cart.getItems().stream()
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .phone(cartItem.getPhone())
                        .quantity(cartItem.getQuantity())
                        .build())
                .collect(Collectors.toList()));
        cart.clear();
        return order;
    }

    @Override
    public void placeOrder(Order order) throws OutOfStockException {
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return new BigDecimal(deliveryPriceProperty);
    }
}
