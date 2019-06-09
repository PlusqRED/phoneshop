package com.es.core.service.phone;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.SortBy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class PhoneServiceImplTest {

    @Resource
    private PhoneService phoneService;

    @Test
    public void testSort() {
        List<Phone> phonesToSort = new ArrayList<>();
        phonesToSort.add(Phone.builder().price(BigDecimal.ONE).build());
        phonesToSort.add(Phone.builder().price(BigDecimal.TEN).build());
        SortBy sortBy = SortBy.PRICE;
        sortBy.setSortOrder(false);
        phoneService.sort(phonesToSort, sortBy);
        assertEquals(phonesToSort.get(0).getPrice(), BigDecimal.TEN);
    }
}
