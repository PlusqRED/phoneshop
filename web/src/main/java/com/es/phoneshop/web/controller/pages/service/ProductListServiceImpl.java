package com.es.phoneshop.web.controller.pages.service;

import com.es.core.model.phone.SortBy;
import com.es.phoneshop.web.controller.pages.pagination.PaginationDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductListServiceImpl implements ProductListService {

    @Resource
    PaginationDetails paginationDetails;

    @Override
    public void checkForSort(String sort, String column) {
        if (sort != null && column != null) {
            SortBy sortBy = SortBy.valueOf(column.toUpperCase().replace(" ", "_"));
            sortBy.setSortOrder(sort.equals("asc"));
            sortBy.sort(paginationDetails.getPagePhones());
        }
    }
}
