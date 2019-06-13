package com.es.phoneshop.web.controller.pages.validators;

import com.es.core.dao.phone.PhoneDao;
import com.es.phoneshop.web.controller.pages.forms.CartPageRequestForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@PropertySource("WEB-INF/properties/messages.properties")
@Component
public class CartPageValidator implements Validator {

    @Value("${quantityError.invalidQuantity}")
    private String invalidQuantity;

    @Value("${quantityError.outOfStock}")
    private String outOfStock;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartPageRequestForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartPageRequestForm cartPageRequestForm = (CartPageRequestForm) o;
        String[] phoneIds = cartPageRequestForm.getPhoneId();
        String[] quantities = cartPageRequestForm.getQuantity();
        if (quantities.length == 0) {
            quantities = new String[]{""};
        }
        for (int i = 0; i < phoneIds.length; ++i) {
            validatePair(phoneIds[i], quantities[i], errors);
        }
    }

    private void validatePair(String productId, String quantity, Errors errors) {
        try {
            Long longQuantity = Long.valueOf(quantity);
            if (longQuantity < 1) {
                errors.reject("quantityError.invalidQuantity", invalidQuantity);
            } else {
                Integer stock = phoneDao.getPhoneStockById(Long.valueOf(productId));
                if (longQuantity > stock) {
                    errors.reject("quantityError.outOfStock", outOfStock);
                } else {
                    errors.reject("", null);
                }
            }
        } catch (NumberFormatException e) {
            errors.reject("quantityError.invalidQuantity", invalidQuantity);
        }
    }
}
