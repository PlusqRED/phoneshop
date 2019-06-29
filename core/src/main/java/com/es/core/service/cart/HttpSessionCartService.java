package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    @Transactional
    public void addPhone(Long phoneId, Long quantity) {
        if (quantity > 0) {
            phoneDao.find(phoneId)
                    .ifPresent(phone -> cart.addCartItem(new CartItem(phone, quantity)));
        }
    }

    @Override
    @Transactional
    public void update(Map<String, String> items) {
        items.forEach((key, value) -> cart.setQuantityByProductId(Long.valueOf(key), Long.valueOf(value)));
    }

    @Override
    @Transactional
    public void remove(Long phoneId) {
        if (phoneId != null) {
            cart.remove(phoneId);
        }
    }
}
