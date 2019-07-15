package com.es.core.service.order;

import com.es.core.dao.order.OrderDao;
import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:properties/order.properties")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Value("${delivery.price}")
    private String deliveryPriceProperty;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public Order createOrder(Order order, Cart cart) {
        order.setDeliveryPrice(getDeliveryPrice());
        order.setSubtotal(cart.getOverallPrice());
        order.setOverallWrappingPrice(cart.getOverallWrappingPrice());
        order.setTotalPrice(cart.getOverallPrice().add(getDeliveryPrice()).add(cart.getOverallWrappingPrice()));
        order.setOrderItems(cart.getItems().stream()
                .map(cartItem -> OrderItem.builder()
                        .orderId(order.getId())
                        .phone(cartItem.getPhone())
                        .quantity(cartItem.getQuantity())
                        .wrapping(cartItem.getWrapping())
                        .wrappingAdditional(cartItem.getWrappingAdditional())
                        .build())
                .collect(Collectors.toList()));
        order.setStatus(OrderStatus.NEW);
        order.setDate(LocalDateTime.now());
        return order;
    }

    @Override
    @Transactional
    public Long placeOrder(Order order, Cart cart) throws OutOfStockException {
        Optional<OrderItem> outOfStockOrderItem = order.getOrderItems().stream()
                .filter(item -> phoneDao.getPhoneStockById(item.getPhone().getId()) < item.getQuantity()).findAny();
        if (outOfStockOrderItem.isPresent()) {
            throw new OutOfStockException(outOfStockOrderItem.get().getPhone().getId());
        }
        return orderDao.save(order);
    }

    @Override
    public BigDecimal getDeliveryPrice() {
        return new BigDecimal(deliveryPriceProperty);
    }
}
