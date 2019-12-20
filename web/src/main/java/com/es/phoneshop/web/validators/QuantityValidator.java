package com.es.phoneshop.web.validators;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.phoneshop.web.forms.AjaxRequestForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

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
        if (isProductIdValid(ajaxRequestForm)) {
            isQuantityValid(ajaxRequestForm, errors);
        } else {
            errors.reject("productIdError.doesntExist", doesntExist);
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

    private boolean isProductIdValid(AjaxRequestForm ajaxRequestForm) {
        return phoneDao.find(ajaxRequestForm.getProductId()).isPresent();
    }
}
