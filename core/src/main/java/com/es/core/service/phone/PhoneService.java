package com.es.core.service.phone;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortBy;

import java.util.List;

public interface PhoneService {
    void sort(List<Phone> phones, SortBy sortBy, boolean asc);
}
