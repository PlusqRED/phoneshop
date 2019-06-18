package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/orderOverview")
public class OrderOverviewPageController {

    @GetMapping
    public String getOderOverview(Model model, @ModelAttribute("order") Order order) {
        model.addAttribute("order", order);
        return "orderOverview";
    }
}
