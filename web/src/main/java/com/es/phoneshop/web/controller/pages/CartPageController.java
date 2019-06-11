package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    @Resource
    private CartService cartService;

    @Resource
    private Cart cart;

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("overallPrice", cart.getOverallPrice());
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(HttpServletRequest request) {
        String[] ids = request.getParameterValues("id");
        String[] quantities = request.getParameterValues("quantity");
        Map<Long, Long> items = new HashMap<>();
        for (int i = 0; i < ids.length; ++i) {
            items.put(Long.valueOf(ids[i]), Long.valueOf(quantities[i]));
        }
        cartService.update(items);
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable("id") long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

}
