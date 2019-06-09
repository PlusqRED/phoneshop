package com.es.phoneshop.web.controller.pages.minicart;

import com.es.core.model.cart.Cart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class MinicartServiceImpl implements MinicartService {

    private static final String CART_PRODUCT_QUANTITY = "cartProductQuantity";

    private static final String OVERALL_PRICE = "overallPrice";

    @Resource
    private Cart cart;

    @Override
    public void loadMinicart(HttpServletRequest request) {
        request.getSession().setAttribute(CART_PRODUCT_QUANTITY, cart.getProductQuantity());
        request.getSession().setAttribute(OVERALL_PRICE, cart.getOverallPrice());
    }
}
