package com.es.phoneshop.web.controller;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.phoneshop.web.exceptions.ItemNotFoundException;
import com.es.phoneshop.web.minicart.MinicartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private MinicartService minicartService;

    @GetMapping("/{id}")
    public String getPhoneInfo(@PathVariable("id") Long id, HttpServletRequest request) throws ItemNotFoundException {
        Optional<Phone> optionalPhone = phoneDao.find(id);
        if (optionalPhone.isPresent()) {
            request.setAttribute("phone", optionalPhone.get());
        } else {
            throw new ItemNotFoundException();
        }
        minicartService.loadMinicart(request);
        return "productDetails";
    }
}
