package com.es.core.dao.phone;

import com.es.core.dao.CrudDao;
import com.es.core.model.phone.Phone;

public interface PhoneDao extends CrudDao<Phone> {
    Integer getPhoneStockById(Long id);
}
