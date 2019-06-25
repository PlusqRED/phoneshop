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
    private PaginationDetails pDet;

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
                && request.getParameter("back") == null
        ) {
            pDet.setSearch(null);
        } else if (search != null) {
            pDet.setSearch(search);
        }
        if (page != null) {
            goPage(Integer.valueOf(page));
        } else if (paginationAction != null) {
            act(PaginationAction.valueOf(paginationAction.toUpperCase()));
        } else {
            initialPage();
        }
        updatePageIndices();
        request.setAttribute("phones", pDet.getPagePhones());
        request.setAttribute("pages", pDet.getPageIndices());
        request.setAttribute("currentPageIndex", pDet.getCurrentPageIndex());
        request.setAttribute("totalNumberOfPhones", pDet.getTotalNumberOfPhonesFound());
    }

    private void initialPage() {
        pDet.setCurrentPageIndex(1);
        pDet.setLeftPageBound(1);
        List<Phone> phones = pDet.getSearch() != null
                ? phoneDao.findAllBySearchQuery(pDet.getSearch(), 0, pDet.getMaxProductsOnPage())
                : phoneDao.findAll(0, pDet.getMaxProductsOnPage());
        pDet.setPagePhones(phones);
    }

    private void updatePageIndices() {
        long phoneAmount = pDet.getSearch() == null
                ? phoneDao.getProductAmount()
                : phoneDao.getProductAmountSearchBased(pDet.getSearch());
        pDet.setTotalNumberOfPhonesFound(phoneAmount);
        int lastPage = (int) (phoneAmount % pDet.getMaxProductsOnPage() == 0
                ? phoneAmount / pDet.getMaxProductsOnPage()
                : (phoneAmount / pDet.getMaxProductsOnPage()) + 1);
        pDet.setLastPage(lastPage);
        validateBounds();
        List<Integer> pageIndices = new ArrayList<>();
        for (int i = pDet.getLeftPageBound(); i <= pDet.getLastPage(); ++i) {
            if (pageIndices.size() < pDet.getMaxVisiblePages()) {
                pageIndices.add(i);
            }
        }
        pDet.setPageIndices(pageIndices);
    }

    private void validateBounds() {
        if (checkForExtensionLeft()) {
            pDet.setLeftPageBound(pDet.getLeftPageBound() - pDet.getMaxVisiblePages() + 1);
        } else if (checkForExtensionRight()) {
            pDet.setLeftPageBound(pDet.getLeftPageBound() + pDet.getMaxVisiblePages() - 1);
        }
    }

    private boolean checkForExtensionRight() {
        return !pDet.getCurrentPageIndex().equals(pDet.getLastPage())
                && pDet.getCurrentPageIndex().equals(pDet.getLeftPageBound() + pDet.getMaxVisiblePages() - 1);
    }

    private boolean checkForExtensionLeft() {
        return pDet.getCurrentPageIndex() != 1 && pDet.getCurrentPageIndex().equals(pDet.getLeftPageBound());
    }

    private void act(PaginationAction action) {
        switch (action) {
            case BACK:
                if (pDet.getCurrentPageIndex() != 1) {
                    goPage(pDet.getCurrentPageIndex() - 1);
                }
                break;
            case FORWARD:
                if (!pDet.getCurrentPageIndex().equals(pDet.getLastPage())) {
                    goPage(pDet.getCurrentPageIndex() + 1);
                }
                break;
        }
    }

    private void goPage(Integer page) {
        pDet.setCurrentPageIndex(page);
        int offset = (pDet.getCurrentPageIndex() - 1) * pDet.getMaxProductsOnPage();
        List<Phone> phones = pDet.getSearch() != null
                ? phoneDao.findAllBySearchQuery(pDet.getSearch(), offset, pDet.getMaxProductsOnPage())
                : phoneDao.findAll(offset, pDet.getMaxProductsOnPage());
        pDet.setPagePhones(phones);
    }
}
