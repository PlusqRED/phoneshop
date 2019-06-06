package com.es.phoneshop.web.controller.pages.ajax;

import com.es.core.model.cart.Cart;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.QuantityValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {

    @Resource
    private Cart cart;

    @Resource
    private CartService cartService;
    @Resource
    private QuantityValidator quantityValidator;

    @PostMapping
    public @ResponseBody
    AjaxResponseForm addPhone(Long productId, Long quantity) {
        boolean valid = quantityValidator.validate(productId, quantity);
        if (valid) {
            cartService.addPhone(productId, quantity);
        }
        return new AjaxResponseForm(cart.getProductQuantity(), cart.getOverallPrice(), valid);
    }

    @AllArgsConstructor
    @Data
    private class AjaxResponseForm {
        private Integer cartProductQuantity;
        private BigDecimal overallPrice;
        private boolean valid;
    }
}
