package com.es.core.service.phone;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortBy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneServiceImpl implements PhoneService {

    @Override
    public void sort(List<Phone> phones, SortBy sortBy) {
        phones.sort(sortBy.isAsc()
                ? SortBy.getComparator(sortBy)
                : SortBy.getComparator(sortBy).reversed()
        );
    }
}
