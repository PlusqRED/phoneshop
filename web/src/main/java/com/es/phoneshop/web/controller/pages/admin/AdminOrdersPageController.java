package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrdersPageController {

    private final static String ORDERS = "orders";
    @Resource
    private OrderDao orderDao;

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = orderDao.findAll(0, Integer.MAX_VALUE);
        model.addAttribute(ORDERS, orders);
        model.addAttribute("localDateTimeFormat", new SimpleDateFormat("yyyy-MM-dd'T'hh:mm"));
        return "adminOrders";
    }
}
