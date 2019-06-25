package com.es.core.dao.phone;

import com.es.core.dao.CrudDao;
import com.es.core.model.phone.Phone;

import java.util.List;

public interface PhoneDao extends CrudDao<Phone> {
    Integer getPhoneStockById(Long id);

    Long getProductAmount();

    Long getProductAmountSearchBased(String search);

    List<Phone> findAllBySearchQuery(String searchQuery, int offset, int limit);

    void decreasePhoneStockById(Long phoneId, Long quantity);
}
