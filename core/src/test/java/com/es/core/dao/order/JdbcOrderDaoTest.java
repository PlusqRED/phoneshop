package com.es.core.dao.order;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class JdbcOrderDaoTest {

    @Resource
    private OrderDao orderDao;

    private final Order order = Order.builder()
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
            .date(LocalDateTime.now())
            .build();

    @Before
    public void setup() {
        orderDao.save(order);
    }

    @Test
    public void findByIdTest() {
        Optional<Order> optionalOrder = orderDao.find(order.getId());
        assertTrue(optionalOrder.isPresent());
    }

    @Test
    public void findItemsByOrderIdTest() {
        List<OrderItem> orderItems = orderDao.findOrderItemsByOrderId(order.getId());
        System.out.println(orderItems);
        assertTrue(orderItems.isEmpty());
    }

    @Test
    public void findAllTest() {
        orderDao.save(order);
        List<Order> orders = orderDao.findAll(0, Integer.MAX_VALUE);
        assertFalse(orders.isEmpty());
    }

    @Test
    public void setOrderStatusTest() {
        orderDao.save(order);
        orderDao.setOrderStatusById(1L, OrderStatus.REJECTED);
        assertEquals(orderDao.find(1L).get().getStatus(), OrderStatus.REJECTED);
    }

}
