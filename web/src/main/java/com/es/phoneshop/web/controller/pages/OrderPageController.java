package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import com.es.core.service.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.util.UUID;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    @Resource
    private OrderService orderService;

    @Resource
    private Cart cart;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("overallPrice", cart.getOverallPrice());
        model.addAttribute("deliveryPrice", orderService.getDeliveryPrice());
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView placeOrder(Model model, @ModelAttribute("order") Order order, RedirectAttributes attributes) throws OutOfStockException {
        order.setId(UUID.randomUUID().getMostSignificantBits());
        orderService.createOrder(order, cart);
        orderService.placeOrder(order);
        attributes.addFlashAttribute("order", order);
        return new RedirectView("orderOverview");
    }
}
