package com.es.core.model.phone;

import com.es.core.model.CrudDao;

import java.util.List;

public interface PhoneDao extends CrudDao<Phone> {
    List<Color> findPhoneColors(Long phoneId);
}
