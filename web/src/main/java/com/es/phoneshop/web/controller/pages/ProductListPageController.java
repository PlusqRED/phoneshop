package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.cart.CartService;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PhoneService phoneService;

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(HttpServletRequest request, String search) {
        List<Phone> phones =
                search != null
                        ? phoneService.findAllBySearchQuery(search, 0, 10)
                        : phoneDao.findAll(0, 10);
        request.setAttribute("phones", phones);
        Cart cart = cartService.getCart();
        request.getSession().setAttribute("cartProductQuantity", cart.getProductQuantity());
        request.getSession().setAttribute("overallPrice", cart.getOverallPrice());
        return "productList";
    }
}
