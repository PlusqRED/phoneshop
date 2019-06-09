package com.es.phoneshop.web.controller.pages.pagination;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService {

    @Resource
    private PaginationDetails paginationDetails;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public void recognizeAndPerformAction(HttpServletRequest request, String search) {
        String page = request.getParameter("page");
        String paginationAction = request.getParameter("pagAct");
        if (page == null
                && paginationAction == null
                && (search == null || search.isEmpty())
                && request.getParameter("sort") == null
        ) {
            paginationDetails.setSearch(null);
        } else if (search != null) {
            paginationDetails.setSearch(search);
        }
        if (page != null) {
            goPage(Integer.valueOf(page));
        } else if (paginationAction != null) {
            act(PaginationAction.valueOf(paginationAction.toUpperCase()));
        } else {
            initialPage();
        }
        updatePageIndices();
        request.setAttribute("phones", paginationDetails.getPagePhones());
        request.setAttribute("pages", paginationDetails.getPageIndices());
        request.setAttribute("currentPageIndex", paginationDetails.getCurrentPageIndex());
    }

    private void initialPage() {
        paginationDetails.setCurrentPageIndex(1);
        paginationDetails.setLeftPageBound(1);
        List<Phone> phones = paginationDetails.getSearch() != null
                ? phoneDao.findAllBySearchQuery(paginationDetails.getSearch(), 0, paginationDetails.getMaxProductsOnPage())
                : phoneDao.findAll(0, paginationDetails.getMaxProductsOnPage());
        paginationDetails.setPagePhones(phones);
    }

    private void updatePageIndices() {
        long phoneAmount = paginationDetails.getSearch() == null
                ? phoneDao.getProductAmount()
                : phoneDao.getProductAmountSearchBased(paginationDetails.getSearch());
        paginationDetails.setLastPage((int) ((phoneAmount / paginationDetails.getMaxProductsOnPage()) + 1));
        validateBounds();
        List<Integer> pageIndices = new ArrayList<>();
        for (int i = paginationDetails.getLeftPageBound(); i <= paginationDetails.getLastPage(); ++i) {
            if (pageIndices.size() < paginationDetails.getMaxVisiblePages()) {
                pageIndices.add(i);
            }
        }
        paginationDetails.setPageIndices(pageIndices);
    }

    private void validateBounds() {
        if (paginationDetails.getCurrentPageIndex() != 1 && paginationDetails.getCurrentPageIndex().equals(paginationDetails.getLeftPageBound())) {
            paginationDetails.setLeftPageBound(paginationDetails.getLeftPageBound() - paginationDetails.getMaxVisiblePages() + 1);
        } else if (!paginationDetails.getCurrentPageIndex().equals(paginationDetails.getLastPage()) && paginationDetails.getCurrentPageIndex().equals(paginationDetails.getLeftPageBound() + paginationDetails.getMaxVisiblePages() - 1)) {
            paginationDetails.setLeftPageBound(paginationDetails.getLeftPageBound() + paginationDetails.getMaxVisiblePages() - 1);
        }
    }

    private void act(PaginationAction action) {
        switch (action) {
            case BACK:
                if (paginationDetails.getCurrentPageIndex() != 1) {
                    goPage(paginationDetails.getCurrentPageIndex() - 1);
                }
                break;
            case FORWARD:
                if (!paginationDetails.getCurrentPageIndex().equals(paginationDetails.getLastPage())) {
                    goPage(paginationDetails.getCurrentPageIndex() + 1);
                }
                break;
        }
    }

    private void goPage(Integer page) {
        paginationDetails.setCurrentPageIndex(page);
        List<Phone> phones = paginationDetails.getSearch() != null
                ? phoneDao.findAllBySearchQuery(paginationDetails.getSearch(), (paginationDetails.getCurrentPageIndex() - 1) * paginationDetails.getMaxProductsOnPage(), paginationDetails.getMaxProductsOnPage())
                : phoneDao.findAll((paginationDetails.getCurrentPageIndex() - 1) * paginationDetails.getMaxProductsOnPage(), paginationDetails.getMaxProductsOnPage());
        paginationDetails.setPagePhones(phones);
    }
}
