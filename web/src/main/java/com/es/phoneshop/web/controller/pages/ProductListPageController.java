package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.service.phone.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;

    @Resource
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, String search) {
        List<Phone> phones =
                search != null
                        ? phoneService.findAllBySearchQuery(search, 0, 10)
                        : phoneDao.findAll(0, 10);
        model.addAttribute("phones", phones);
        return "productList";
    }
}
