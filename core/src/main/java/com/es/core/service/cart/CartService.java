package com.es.core.service.cart;

import java.util.Map;

public interface CartService {

    void addPhone(Long phoneId, Long quantity, Boolean wrapping, String wrappingAdditional);

    /**
     * @param items key: {@link com.es.core.model.phone.Phone#}
     *              value: quantity
     */
    void update(Map<String, String> items);

    void remove(Long phoneId);
}
