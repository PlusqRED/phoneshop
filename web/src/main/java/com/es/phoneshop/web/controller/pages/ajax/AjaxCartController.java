package com.es.phoneshop.web.controller.pages.ajax;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.phoneshop.web.controller.pages.forms.AjaxRequestForm;
import com.es.phoneshop.web.controller.pages.forms.AjaxResponseForm;
import com.es.phoneshop.web.controller.pages.validators.QuantityValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    AjaxResponseForm addPhone(@RequestBody @Validated AjaxRequestForm ajaxRequestForm, BindingResult bindingResult) {
        AjaxResponseForm.AjaxResponseFormBuilder responseBuilder = AjaxResponseForm.builder()
                .cartQuantity(cart.getProductQuantity())
                .overallPrice(cart.getOverallPrice().toString());
        if (!bindingResult.hasErrors()) {
            cartService.addPhone(ajaxRequestForm.getProductId(), Long.valueOf(ajaxRequestForm.getQuantity()));
            return responseBuilder.errorMessage(null).build();
        }
        return responseBuilder.errorMessage(bindingResult.getAllErrors().get(0).getDefaultMessage()).build();
    }
}
