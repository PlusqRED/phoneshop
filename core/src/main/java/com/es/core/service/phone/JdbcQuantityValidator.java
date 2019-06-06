package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class JdbcQuantityValidator implements QuantityValidator {

    @Resource
    private PhoneDao phoneDao;

    @Resource
    private Cart cart;

    @Override
    public boolean validate(Long productId, Long quantity) {
        Optional<Phone> phone = phoneDao.find(productId);
        if (phone.isPresent()) {
            return quantity > 0 && phoneDao.getPhoneStockById(productId) > (quantity + cart.getQuantityByProductId(productId));
        } else {
            return false;
        }
    }
}
