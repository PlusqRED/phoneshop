package com.es.core.dao.phone;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class JdbcOrderDaoTest {

    @Resource
    private OrderDao orderDao;

    private Order order = Order.builder()
            .id(1L)
            .status(OrderStatus.NEW)
            .subtotal(BigDecimal.ONE)
            .deliveryPrice(BigDecimal.ONE)
            .firstName("test")
            .lastName("test")
            .deliveryAddress("test")
            .contactPhoneNo("test")
            .additionalInformation("test")
            .orderItems(new ArrayList<>())
            .build();

    @Before
    public void setup() {
        orderDao.saveOrder(order);
    }

    @Test
    public void findByIdTest() {
        Optional<Order> optionalOrder = orderDao.findById(order.getId());
        assertTrue(optionalOrder.isPresent());
    }

    @Test
    public void findItemsByOrderIdTest() {
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(order.getId());
        assertTrue(orderItems.isEmpty());
    }

}
