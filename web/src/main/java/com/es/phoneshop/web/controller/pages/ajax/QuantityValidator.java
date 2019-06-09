package com.es.phoneshop.web.controller.pages.ajax;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;
import java.util.Optional;

@Component
@PropertySource("WEB-INF/properties/messages.properties")
public class QuantityValidator implements Validator {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private Cart cart;

    @Value("${productIdError.doesntExist}")
    private String doesntExist;

    @Value("${productIdError.invalidProductId}")
    private String invalidProductId;

    @Value("${quantityError.invalidQuantity}")
    private String invalidQuantity;

    @Value("${quantityError.outOfStock}")
    private String outOfStock;

    @Override
    public boolean supports(Class<?> aClass) {
        return AjaxRequestForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AjaxRequestForm ajaxRequestForm = (AjaxRequestForm) o;
        if (isProductIdValid(ajaxRequestForm, errors)) {
            isQuantityValid(ajaxRequestForm, errors);
        } else {
            errors.reject("productIdError.invalidProductId", invalidProductId);
        }
    }

    private boolean isQuantityValid(AjaxRequestForm ajaxRequestForm, Errors errors) {
        Long quantity;
        try {
            quantity = Long.valueOf(ajaxRequestForm.getQuantity());
        } catch (NumberFormatException e) {
            errors.reject("quantityError.invalidQuantity", invalidQuantity);
            return false;
        }
        if (quantity < 1) {
            errors.reject("quantityError.invalidQuantity", invalidQuantity);
            return false;
        } else if (phoneDao.getPhoneStockById(ajaxRequestForm.getProductId()) < (quantity + cart.getQuantityByProductId(ajaxRequestForm.getProductId()))) {
            errors.reject("quantityError.outOfStock", outOfStock);
            return false;
        }
        return true;
    }

    private boolean isProductIdValid(AjaxRequestForm ajaxRequestForm, Errors errors) {
        Optional<Phone> phone = phoneDao.find(ajaxRequestForm.getProductId());
        if (phone.isPresent()) {
            return true;
        } else {
            errors.reject("productIdError.doesntExist", doesntExist);
            return false;
        }
    }
}
