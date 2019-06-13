package com.es.phoneshop.web.controller.pages.forms;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CartPageRequestForm {
    private String[] phoneId;
    private String[] quantity;
    private Map<Long, Long> phoneIdsToQuantities;

    public Map<Long, Long> getPhoneIdsToQuantities() {
        phoneIdsToQuantities = new HashMap<>();
        for (int i = 0; i < phoneId.length; ++i) {
            phoneIdsToQuantities.put(Long.valueOf(phoneId[i]), Long.valueOf(quantity[i]));
        }
        return phoneIdsToQuantities;
    }
}
