package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.phoneshop.web.controller.pages.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders/")
public class AdminOrdersOverviewPageController {

    @Resource
    private OrderDao orderDao;

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model) throws ItemNotFoundException {
        Optional<Order> optionalOrder = orderDao.find(id);
        if (optionalOrder.isPresent()) {
            model.addAttribute("order", optionalOrder.get());
            return "adminOrderOverview";
        } else {
            throw new ItemNotFoundException();
        }
    }

    @PostMapping("/{id}")
    public String setStatus(@PathVariable Long id, @RequestParam String status) {
        orderDao.setOrderStatusById(id, OrderStatus.valueOf(status.toUpperCase()));
        return "redirect:/admin/orders/" + id;
    }
}
