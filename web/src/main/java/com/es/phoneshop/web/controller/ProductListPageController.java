package com.es.phoneshop.web.controller;

import com.es.phoneshop.web.minicart.MinicartService;
import com.es.phoneshop.web.pagination.PaginationService;
import com.es.phoneshop.web.service.ProductListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String showProductList(HttpServletRequest request, String search, String sort, String column) {
        paginationService.recognizeAndPerformAction(request, search);
        productListService.checkForSort(sort, column);
        minicartService.loadMinicart(request);
        return "productList";
    }
}
