package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.SortBy;
import com.es.core.service.phone.PhoneService;
import com.es.phoneshop.web.controller.pages.minicart.MinicartService;
import com.es.phoneshop.web.controller.pages.pagination.PaginationDetails;
import com.es.phoneshop.web.controller.pages.pagination.PaginationService;
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
    private PhoneService phoneService;

    @Resource
    private PaginationDetails paginationDetails;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(HttpServletRequest request, String search, String sort, String column) {
        paginationService.recognizeAndPerformAction(request, search);
        if (sort != null && column != null) {
            SortBy sortBy = SortBy.valueOf(column.toUpperCase().replace(" ", "_"));
            sortBy.setSortOrder(sort.equals("asc"));
            phoneService.sort(
                    paginationDetails.getPagePhones(),
                    SortBy.valueOf(column.toUpperCase().replace(" ", "_"))
            );
        }
        minicartService.loadMinicart(request);
        return "productList";
    }
}
