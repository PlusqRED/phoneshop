package com.es.phoneshop.web.controller.pages.pagination;

import javax.servlet.http.HttpServletRequest;

public interface PaginationService {

    void recognizeAndPerformAction(HttpServletRequest request, String search);
}
