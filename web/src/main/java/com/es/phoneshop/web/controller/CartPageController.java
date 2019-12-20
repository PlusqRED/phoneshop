package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.forms.CartPageRequestForm;
import com.es.phoneshop.web.validators.CartPageValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {

    private final static String FORM = "cartPageRequestForm";
    @Resource
    private CartService cartService;
    @Resource
    private Cart cart;
    @Resource
    private CartPageValidator cartPageValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(cartPageValidator);
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("overallPrice", cart.getOverallPrice());
        if (!model.containsAttribute(FORM)) {
            model.addAttribute(FORM, new CartPageRequestForm());
        }
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(
            @ModelAttribute @Validated CartPageRequestForm cartPageRequestForm,
            BindingResult result,
            Model model
    ) {
        model.addAttribute("hasErrors", result.hasErrors());
        if (result.hasErrors()) {
            return getCart(model);
        } else {
            cartService.update(cartPageRequestForm.getQuantities());
            return "redirect:/cart";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

}
