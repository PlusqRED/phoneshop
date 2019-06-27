package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.order.OrderDao;
import com.es.core.model.order.Order;
import com.es.phoneshop.web.controller.pages.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @Resource
    private OrderDao orderDao;

    @GetMapping("/{id}")
    public String getOderOverview(Model model, @PathVariable Long id) throws ItemNotFoundException {
        Optional<Order> optionalOrder = orderDao.find(id);
        if (optionalOrder.isPresent()) {
            model.addAttribute("order", optionalOrder.get());
            return "orderOverview";
        } else {
            throw new ItemNotFoundException();
        }
    }
}
