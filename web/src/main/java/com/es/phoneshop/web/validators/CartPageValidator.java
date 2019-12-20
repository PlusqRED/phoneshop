package com.es.phoneshop.web.validators;

import com.es.core.dao.phone.PhoneDao;
import com.es.phoneshop.web.forms.CartPageRequestForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class CartPageValidator implements Validator {
    private final static String PATH = "quantities['%s']";

    @Resource
    private PhoneDao phoneDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return CartPageRequestForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CartPageRequestForm cartPageRequestForm = (CartPageRequestForm) o;
        cartPageRequestForm.getQuantities().forEach((key, value) -> validatePair(key, value, errors));
    }

    private void validatePair(String productId, String quantity, Errors errors) {
        try {
            Long longQuantity = Long.valueOf(quantity);
            if (longQuantity < 1) {
                errors.rejectValue(String.format(PATH, productId), "quantityError.invalidQuantity");
            } else {
                Integer stock = phoneDao.getPhoneStockById(Long.valueOf(productId));
                if (longQuantity > stock) {
                    errors.rejectValue(String.format(PATH, productId), "quantityError.outOfStock");
                }
            }
        } catch (NumberFormatException e) {
            errors.rejectValue(String.format(PATH, productId), "quantityError.invalidQuantity");
        }
    }
}
