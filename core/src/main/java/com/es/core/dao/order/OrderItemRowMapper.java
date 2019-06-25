package com.es.core.dao.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    private final PhoneDao phoneDao;

    public OrderItemRowMapper(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        return OrderItem.builder()
                .orderId(resultSet.getLong("ORDER_ID"))
                .phone(phoneDao.find(resultSet.getLong("PHONE_ID")).get())
                .id(resultSet.getLong("ID"))
                .quantity(resultSet.getLong("quantity"))
                .build();
    }
}
