package com.es.core.dao.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderResultSetExtractor implements ResultSetExtractor<List<Order>> {

    private PhoneDao phoneDao;

    public OrderResultSetExtractor(PhoneDao phoneDao) {
        this.phoneDao = phoneDao;
    }

    @Override
    public List<Order> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Order> orderMap = new HashMap<>();
        OrderItemRowMapper orderItemResultSetExtractor = new OrderItemRowMapper(phoneDao);
        OrderRowMapper orderRowMapper = new OrderRowMapper();
        while (resultSet.next()) {
            Order order = orderRowMapper.mapRow(resultSet, 0);
            OrderItem orderItem = orderItemResultSetExtractor.mapRow(resultSet, 0);
            orderMap.computeIfAbsent(order.getId(), key -> order)
                    .getOrderItems()
                    .add(orderItem);
        }
        return new ArrayList<>(orderMap.values());
    }
}
