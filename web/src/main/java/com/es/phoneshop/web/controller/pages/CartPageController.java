package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.forms.CartPageRequestForm;
import com.es.phoneshop.web.controller.pages.validators.CartPageValidator;
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

    @PostMapping("/update")
    public String updateCart(
            @ModelAttribute @Validated CartPageRequestForm cartPageRequestForm,
            BindingResult result,
            Model model
    ) {
        model.addAttribute("hasErrors", result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("cartItems", cart.getItems());
            model.addAttribute("overallPrice", cart.getOverallPrice());
            return "cart";
        } else {
            cartService.update(cartPageRequestForm.getQuantities());
            return "redirect:/cart";
        }
    }

    @Resource
    private CartService cartService;

    @Resource
    private Cart cart;

    @Resource
    private CartPageValidator cartPageValidator;

    private final static String FORM = "cartPageRequestForm";

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(cartPageValidator);
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("overallPrice", cart.getOverallPrice());
        model.addAttribute(FORM, new CartPageRequestForm());
        return "cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

}
