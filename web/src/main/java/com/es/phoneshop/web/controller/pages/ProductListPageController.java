package com.es.phoneshop.web.controller.pages;

import com.es.phoneshop.web.controller.pages.minicart.MinicartService;
import com.es.phoneshop.web.controller.pages.pagination.PaginationService;
import com.es.phoneshop.web.controller.pages.service.ProductListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private MinicartService minicartService;

    @Resource
    private PaginationService paginationService;

    @Resource
    private ProductListService productListService;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(HttpServletRequest request, String search, String sort, String column) {
        paginationService.recognizeAndPerformAction(request, search);
        productListService.checkForSort(sort, column);
        minicartService.loadMinicart(request);
        return "productList";
    }
}
