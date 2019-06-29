package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.service.order.OrderService;
import com.es.core.service.order.OutOfStockException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {

    private final static String OUT_OF_STOCK = "outOfStock";

    @Value("${order.emptyOrderList}")
    private String emptyOrderList;

    @Resource
    private OrderService orderService;

    @Resource
    private Cart cart;

    @GetMapping
    public String getOrder(HttpServletRequest request) {
        request.setAttribute("cartItems", cart.getItems());
        request.setAttribute("overallPrice", cart.getOverallPrice());
        request.setAttribute("deliveryPrice", orderService.getDeliveryPrice());
        return "order";
    }

    @PostMapping
    @Transactional
    public String placeOrder(@ModelAttribute("order") Order order) {
        orderService.createOrder(order, cart);
        try {
            String redirectUrl = "redirect:orderOverview/";
            if (!order.getOrderItems().isEmpty()) {
                Long placedOrderId = orderService.placeOrder(order);
                cart.clear();
                return redirectUrl + placedOrderId;
            } else {
                return "redirect:order?err=" + emptyOrderList;
            }
        } catch (OutOfStockException ex) {
            String model = cart.getModelById(ex.getOutOfStockPhoneId());
            cart.remove(ex.getOutOfStockPhoneId());
            return "redirect:order?err=" + model + " is out of stock!";
        }
    }
}
