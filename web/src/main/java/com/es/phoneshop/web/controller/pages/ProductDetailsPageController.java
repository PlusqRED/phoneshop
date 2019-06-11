package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.PhoneDao;
import com.es.phoneshop.web.controller.pages.minicart.MinicartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private MinicartService minicartService;

    @GetMapping("/{id}")
    public String getPhoneInfo(@PathVariable("id") long id, HttpServletRequest request) {
        phoneDao.find(id).ifPresent(phone -> request.setAttribute("phone", phone));
        minicartService.loadMinicart(request);
        return "productDetails";
    }
}
