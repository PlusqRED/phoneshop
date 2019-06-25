package com.es.core.dao.order;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = Order.builder()
                .id(resultSet.getLong("ID"))
                .subtotal(resultSet.getBigDecimal("SUBTOTAL"))
                .deliveryPrice(resultSet.getBigDecimal("DELIVERY_PRICE"))
                .firstName(resultSet.getString("FIRST_NAME"))
                .lastName(resultSet.getString("LAST_NAME"))
                .deliveryAddress(resultSet.getString("DELIVERY_ADDRESS"))
                .contactPhoneNo(resultSet.getString("CONTACT_PHONE_NO"))
                .additionalInformation(resultSet.getString("ADDITIONAL_INFO"))
                .status(OrderStatus.valueOf(resultSet.getString("ORDER_STATUS").toUpperCase()))
                .orderItems(new ArrayList<>())
                .date(resultSet.getTimestamp("DATE").toLocalDateTime())
                .build();
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        return order;
    }
}
