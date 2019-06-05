package com.es.core.service.phone;

import com.es.core.dao.phone.PhoneDao;
import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PhoneServiceImpl implements PhoneService {

    @Resource
    private PhoneDao phoneDao;

    @Override
    public List<Phone> findAllBySearchQuery(String searchQuery, int offset, int limit) {
        List<Phone> all = phoneDao.findAll(0, Integer.MAX_VALUE);
        String[] words = searchQuery
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", " ").split(" ");

        return all.stream()
                .filter(phone -> Arrays.stream(words).anyMatch(word ->
                        phone.getBrand()
                                .concat(phone.getModel())
                                .toLowerCase()
                                .contains(word)))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
