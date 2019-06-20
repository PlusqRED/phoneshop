package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.forms.CartPageRequestForm;
import com.es.phoneshop.web.controller.pages.service.CartPageResponseService;
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

    @Resource
    private CartService cartService;

    @Resource
    private Cart cart;

    @Resource
    private CartPageValidator cartPageValidator;

    @Resource
    private CartPageResponseService cartPageResponseService;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(cartPageValidator);
    }

    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("cartItems", cart.getItems());
        model.addAttribute("overallPrice", cart.getOverallPrice());
        model.addAttribute("cartPageRequestForm", new CartPageRequestForm());
        if (!cartPageResponseService.isViewed()) {
            model.addAttribute("errors", cartPageResponseService.getErrors());
            cartPageResponseService.setViewed(true);
        }
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(
            @ModelAttribute("cartPageRequestForm") @Validated CartPageRequestForm cartPageRequestForm,
            BindingResult result
    ) {
        boolean hasError = result.getAllErrors().stream()
                .anyMatch(error -> error.getDefaultMessage() != null);
        if (hasError) {
            cartPageResponseService.updateErrors(result.getAllErrors());
        } else {
            cartService.update(cartPageRequestForm.getPhoneIdsToQuantities());
        }
        return "redirect:/cart";
    }

    @PostMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

}
