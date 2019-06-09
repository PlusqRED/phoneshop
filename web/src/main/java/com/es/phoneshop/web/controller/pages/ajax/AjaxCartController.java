package com.es.phoneshop.web.controller.pages.ajax;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private Cart cart;

    @Resource
    private CartService cartService;

    @Resource
    private QuantityValidator quantityValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(quantityValidator);
    }

    @PostMapping
    public @ResponseBody
    AjaxResponseForm addPhone(@RequestBody AjaxRequestForm ajaxRequestForm, BindingResult bindingResult) {
        quantityValidator.validate(ajaxRequestForm, bindingResult);
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(ajaxRequestForm.getProductId(), Long.valueOf(ajaxRequestForm.getQuantity()));
            return new AjaxResponseForm(cart.getProductQuantity(), cart.getOverallPrice(), null);
        }
        return new AjaxResponseForm(cart.getProductQuantity(), cart.getOverallPrice(), bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
}
