package com.es.core.dao.order;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcOrderDao implements OrderDao {

    //language=SQL
    private final static String FIND_BY_ID =
            "select * from ORDERS where ID = ?";

    //language=SQL
    private final static String FIND_ORDER_ITEMS_BY_ORDER_ID =
            "select * from ORDER_ITEMS where ORDER_ID = ?";

    //language=SQL
    private final static String FIND_ALL =
            "select * from (select * from ORDERS offset ? limit ?) t " +
                    "left join ORDER_ITEMS on t.ID = ORDER_ITEMS.ORDER_ID " +
                    "left join PHONES on PHONES.ID = ORDER_ITEMS.PHONE_ID";

    //language=SQL
    private final static String UPDATE_STATUS =
            "update ORDERS set ORDER_STATUS = ? where ID = ?";

    //language=SQL
    private final static String GET_STATUS =
            "select ORDER_STATUS from ORDERS where ID = ?";

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public long save(Order order) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ORDERS")
                .usingGeneratedKeyColumns("ID");
        long orderId = simpleJdbcInsert.executeAndReturnKey(getOrderParameters(order)).longValue();
        order.getOrderItems().forEach(orderItem -> saveOrderItem(orderId, orderItem));
        return orderId;
    }

    private void saveOrderItem(long orderId, OrderItem orderItem) {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ORDER_ITEMS")
                .execute(getOrderItemParameters(orderId, orderItem));
        phoneDao.decreasePhoneStockById(orderItem.getPhone().getId(), orderItem.getQuantity());
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(Long orderId) {
        return jdbcTemplate.query(FIND_ORDER_ITEMS_BY_ORDER_ID, new OrderItemRowMapper(phoneDao), orderId);
    }

    @Override
    public void setOrderStatusById(Long id, OrderStatus status) {
        if (OrderStatus.NEW.equals(getOrderStatusById(id))) {
            jdbcTemplate.update(UPDATE_STATUS, status.toString(), id);
        }
    }

    @Override
    public OrderStatus getOrderStatusById(Long id) {
        return OrderStatus.valueOf(jdbcTemplate.queryForObject(
                GET_STATUS,
                (resultSet, i) -> resultSet.getString("ORDER_STATUS"),
                id
        ).toUpperCase());
    }

    private Map<String, Object> getOrderParameters(Order order) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SUBTOTAL", order.getSubtotal());
        parameters.put("DELIVERY_PRICE", order.getDeliveryPrice());
        parameters.put("OVERALL_WRAPPING_PRICE", order.getOverallWrappingPrice());
        parameters.put("FIRST_NAME", order.getFirstName());
        parameters.put("LAST_NAME", order.getLastName());
        parameters.put("DELIVERY_ADDRESS", order.getDeliveryAddress());
        parameters.put("CONTACT_PHONE_NO", order.getContactPhoneNo());
        parameters.put("ADDITIONAL_INFO", order.getAdditionalInformation());
        parameters.put("ORDER_STATUS", order.getStatus().toString());
        parameters.put("DATE", order.getDate());
        return parameters;
    }

    private Map<String, Object> getOrderItemParameters(Long orderId, OrderItem orderItem) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ORDER_ID", orderId);
        parameters.put("PHONE_ID", orderItem.getPhone().getId());
        parameters.put("ID", orderItem.getId());
        parameters.put("QUANTITY", orderItem.getQuantity());
        parameters.put("WRAPPING", orderItem.getWrapping());
        parameters.put("WRAPPING_ADDITIONAL", orderItem.getWrappingAdditional());
        return parameters;
    }

    @Override
    public Optional<Order> find(Long id) {
        try {
            Order order = jdbcTemplate.queryForObject(FIND_BY_ID, new OrderRowMapper(), id);
            order.setOrderItems(findOrderItemsByOrderId(id));
            return Optional.of(order);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Order model) {

    }

    @Override
    public void delete(Order model) {

    }

    @Override
    public List<Order> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL, new OrderResultSetExtractor(phoneDao), offset, limit);
    }
}
