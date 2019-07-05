package com.es.phoneshop.web.pagination;

import javax.servlet.http.HttpServletRequest;

public interface PaginationService {

    void recognizeAndPerformAction(HttpServletRequest request, String search);
}
