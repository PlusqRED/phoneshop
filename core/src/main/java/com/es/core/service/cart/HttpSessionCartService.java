package com.es.core.service.cart;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HttpSessionCartService implements CartService {

    @Resource
    private Cart cart;

    @Resource
    private PhoneDao phoneDao;

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        if (quantity > 0) {
            phoneDao.find(phoneId)
                    .ifPresent(phone -> cart.addCartItem(new CartItem(phone, quantity)));
        }
    }

    @Override
    public void update(Map<Long, Long> items) {
        List<Long> ids = new ArrayList<>(items.keySet());
        List<Long> quantities = new ArrayList<>(items.values());
        for (int i = 0; i < ids.size(); ++i) {
            cart.setQuantityByProductId(ids.get(i), quantities.get(i));
        }
    }

    @Override
    public void remove(Long phoneId) {
        if (phoneId != null) {
            cart.remove(phoneId);
        }
    }
}
